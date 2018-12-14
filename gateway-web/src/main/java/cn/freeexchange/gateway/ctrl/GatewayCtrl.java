package cn.freeexchange.gateway.ctrl;

import static com.alibaba.fastjson.JSON.toJSONString;
import static cn.freeexchange.common.base.req.RequestDTO.HEADER_PARTNER_ID;
import static cn.freeexchange.common.base.utils.ServletUtils.getRemoteIp;
import static cn.freeexchange.common.base.utils.ServletUtils.parseHeaderMap;
import static cn.freeexchange.common.base.utils.ServletUtils.setResponseHeader;
import static cn.freeexchange.common.base.utils.ServletUtils.writerResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.freeexchange.gateway.domain.BaseResponseBody;
import cn.freeexchange.gateway.domain.interact.Interact;
import cn.freeexchange.gateway.service.InteractService;
import cn.freeexchange.gateway.service.PartnerService;

@Controller
public class GatewayCtrl {
	
	
	//logger for class GatewayCtrl
	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayCtrl.class);
	
	@Resource
	private InteractService interactService;

	@Resource
	private PartnerService partnerService;
	
	@Value("${gateway.ip.proxy}")
	private String gatewayIpProxy;
	
	
	@RequestMapping(method = RequestMethod.POST, value = { "/gw/interact", "/interact" })
	public void interact(HttpServletRequest request, @RequestBody String requestBody,
			HttpServletResponse response) {
		boolean gatewayIpProxy = Boolean.parseBoolean(this.gatewayIpProxy);
		response.setContentType("application/json;charset=UTF-8");
        Map<String, String> requestHeader = parseHeaderMap(request);
        
        String ip = gatewayIpProxy ? getRemoteIp(request) : request.getRemoteAddr();
        
        LOGGER.debug("网关系统"+ (gatewayIpProxy?"【打开了】":"【关闭了】") +"IP代理开关，客户端访问的IP为："+ ip);
        
        try {
        	Interact interact = interactService.interact(requestHeader, requestBody, ip);
        	setResponseHeader(response, interact.getResponseHeader().getContent());
        	writerResponseBody(response, interact.getResponseBody());
        } catch (Throwable t) {
        	LOGGER.error("@@AcquirerCtrl.interact meet error: 【{}】 . request header is 【{}】 and request body is 【{}】 which comes from 【{}】",
                    t,
                    requestHeader,
                    requestBody,
                    ip
            );
        	String origPartnerId = requestHeader.get(HEADER_PARTNER_ID);
            String body = toJSONString(new BaseResponseBody(t));
            buildResponseHeader(origPartnerId, body, response);
            writerResponseBody(response, body);
        }
        

	}
	
	@SuppressWarnings("unchecked")
	private void buildResponseHeader(String orgiPartnerId, String body, HttpServletResponse response){
        if(StringUtils.isNotBlank(orgiPartnerId)) {
            try {
                String sign = partnerService.calcSgin(Long.parseLong(orgiPartnerId), body);
                setResponseHeader(response, makeMap("sign", sign));
            } catch (Throwable thr) {
                LOGGER.error("@@calcSgin meet error.", thr);
            }
        }
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map makeMap(Object... v) {
        if (v.length % 2 != 0) throw new RuntimeException("值长度[" + v.length + "]不是2的倍数,不能映射成Map!");

        Map map = new LinkedHashMap<Object, Object>();
        for (int i = 0; i + 1 < v.length; i += 2) {
            map.put(v[i], v[i + 1]);
        }
        return map;
    }


}
