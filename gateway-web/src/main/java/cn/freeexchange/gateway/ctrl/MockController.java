package cn.freeexchange.gateway.ctrl;

import static com.alibaba.fastjson.JSON.toJSONString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.common.base.utils.ServletUtils;
import cn.freeexchange.gateway.domain.BaseResponseBody;
import cn.freeexchange.gateway.domain.interact.Interact;
import cn.freeexchange.gateway.domain.partner.PortalPolicyHelper;
import cn.freeexchange.gateway.domain.rpt.PartnerRpt;
import cn.freeexchange.gateway.service.InteractService;

/**
 * 
 */
@RestController
@RequestMapping("/mock")
public class MockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockController.class);

    
    @Value("${gateway.ip.proxy}")
	private String gatewayIpProxy;

    @Resource
    private InteractService interactService;

    @Resource
    private PartnerRpt partnerRpt;

    @RequestMapping("/sha256")
    @ResponseBody
    public List<String> sha256(HttpServletRequest request, HttpServletResponse response) {
        //signature是MD5KEY的原文，如：MN7O2E1CG9KGOO7ZL33NTFS07G27GJQ1
        String signature = request.getParameter("signature");
        String requestBody = request.getParameter("requestBody");
        if (StringUtils.isBlank(signature)) throw new RuntimeException("SHA256Key（SignatureKey）不能为空");
        String result = PortalPolicyHelper.calcSign(requestBody, "SHA256", signature);
        List<String> list = new ArrayList<>();
        list.add(result);
        return list;
    }


    @RequestMapping("/doMock")
    @ResponseBody
    public Map<String, Object> doMock(HttpServletRequest request, HttpServletResponse response) {
        String sign = request.getParameter("sign");//优先使用页面上签名好的sign参数值
        String requestBody = request.getParameter("requestBody");
        
        Map<String, String> requestHeader = ServletUtils.parseHeaderMap(request);
        Map<String, Object> map = new HashMap<>();
        requestHeader.put("partner", request.getParameter("partner"));
        requestHeader.put("version", request.getParameter("version"));
        
        if ("1".equals(request.getParameter("algorithm"))) { //MD5
            if(StringUtils.isBlank(sign)){
                //signature是MD5KEY的原文，如：MN7O2E1CG9KGOO7ZL33NTFS07G27GJQ1
                String signature = request.getParameter("signature");
                if (StringUtils.isBlank(signature)) throw new RuntimeException("MD5Key（SignatureKey）不能为空");
                sign = PortalPolicyHelper.calcSign(requestBody, "SHA256", signature);
            }
        }
        if(!StringUtils.isBlank(sign)) {
        	requestHeader.put("sign", sign);
            map.put("sign", sign);
        }
        try {
        	boolean gatewayIpProxy = Boolean.parseBoolean(this.gatewayIpProxy);
            String ip = gatewayIpProxy?ServletUtils.getRemoteIp(request):request.getRemoteAddr();
            LOGGER.info("网关系统"+ (gatewayIpProxy?"【打开了】":"【关闭了】") +"IP代理开关，客户端访问的IP为："+ ip);
            Interact interact = interactService.interact(requestHeader, requestBody, ip);
            map.put("responseBody", interact.getResponseBody());
        } catch (Throwable t) {
            LOGGER.error("@@/mock/doMock meet error.", t);
            LOGGER.error("@@MockController.doMock requestHeader is {}", requestHeader);
            map.put("responseBody", toJSONString(makeResponseBody(t)));
        }
        return map;
    }

    private BaseResponseBody makeResponseBody(Throwable t){
        if (t instanceof BusinessException) {
            BusinessException be = (BusinessException) t;
            return new BaseResponseBody(be.getBusinessCode(), be.getMessage());
        } else {
            return new BaseResponseBody(BaseResponseBody.CODE_SYSTEM_ERROR_CODE, t.getMessage());
        }
    }

    @RequestMapping("/verifySign")
    @ResponseBody
    public Map<String, Object> verifySign(HttpServletRequest request) {
    	Map<String, Object> map = new HashMap<String,Object>();
        String sign = request.getParameter("sign");//优先使用页面上签名好的sign参数值
        String requestBody = request.getParameter("requestBody");
        if(StringUtils.isBlank(sign)){
            map.put("responseBody", "签名不能为空");
            return map;
        }
        if(StringUtils.isBlank(requestBody)){
            map.put("responseBody", "请求报文不能为空");
            return map;
        }

        boolean result = false;
        if ("1".equals(request.getParameter("algorithm"))) { //MD5
            //signature是MD5KEY的原文，如：MN7O2E1CG9KGOO7ZL33NTFS07G27GJQ1
            String signature = request.getParameter("signature");
            if (StringUtils.isBlank(signature)) throw new RuntimeException("SHA256（SignatureKey）不能为空");
            result = PortalPolicyHelper.verifySign(requestBody,sign, "SHA256", signature);
        }
        map.put("responseBody", result);
        return map;
    }
}
