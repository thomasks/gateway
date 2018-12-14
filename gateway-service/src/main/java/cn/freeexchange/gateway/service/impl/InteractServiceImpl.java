package cn.freeexchange.gateway.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.common.base.service.TokenService;
import cn.freeexchange.gateway.domain.BaseRequestBody;
import cn.freeexchange.gateway.domain.interact.Header;
import cn.freeexchange.gateway.domain.interact.Interact;
import cn.freeexchange.gateway.domain.partner.Partner;
import cn.freeexchange.gateway.domain.partner.PortalPolicyHelper;
import cn.freeexchange.gateway.domain.rpt.InteractRpt;
import cn.freeexchange.gateway.domain.rpt.PartnerRpt;
import cn.freeexchange.gateway.domain.rpt.ServiceRpt;
import cn.freeexchange.gateway.service.InteractService;
import cn.freeexchange.gateway.service.utils.MessageFormatUtils;


@Service("interactService")
public class InteractServiceImpl implements InteractService {
	
	public static final String GATEWAY_INTERACT_REQUEST_KEY = "gw:ir_{0}_{1}_{2}_{3}";
	
	//logger for class InteractServiceImpl
	private static final Logger LOGGER = LoggerFactory.getLogger(InteractServiceImpl.class);
	
	@Resource
    private InteractRpt interactRpt;

    @Resource
    private ServiceRpt serviceRpt;

    @Resource
    private PartnerRpt partnerRpt;
    
    @Resource(name = "interactService")
    private InteractService service;
    
    @SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
    
    @Value("${gateway.check.repeat.request.seconds}")
    private String checkRepeatRequestSeconds="3";
    
    
    @Autowired
    private TokenService tokenService;
    
    
	@Override
	@Transactional
	public Interact interact(Map<String, String> headerMap, String body, String ip) {
		LOGGER.debug("---------Gateway interact()方法---------");
        LOGGER.debug("参数headerMap："+ headerMap);
        LOGGER.debug("参数requestBody："+ body);
        LOGGER.debug("参数ip："+ ip);
        
        try {
            Interact newInteract = service.doCreate(headerMap, body, ip);
            Interact execInteract = interactRpt.getOne(newInteract.getId());
            execInteract.exec();
            return execInteract;
        } catch (JSONException t){
            throw new BusinessException("gateway11");
        } catch (Throwable t) {
            throw t;
        }
	}

	@Override
	@Transactional
	public Interact doCreate(Map<String, String> headerMap, String body, String ip) {
		 Header header = new Header(headerMap);
	     header.checkParamMustRequired();
	     BaseRequestBody requestBody = buildRequestBody(header, body);

	     Long partnerId = header.getPartnerId();
	     Partner partner = partnerRpt.getOne(partnerId);
	     if(partner == null) throw new BusinessException("gateway15", String.valueOf(partnerId));
	     String sign = header.getSign();
	     String token = header.getToken();
	     if (StringUtils.isNotBlank(token)) {
        	checkToken(header);
	     } else if (StringUtils.isNotBlank(sign)) {
            checkSign(header, requestBody.getContent(),partner);
	     } else {
            throw new BusinessException("gateway10", "sign或token");
	     }
	     Interact interact = partner.createInteract(header, requestBody, ip);
	     interactRpt.save(interact);
	     return interact;
	}
	
	private void checkToken(Header header) {
		 String clientToken = header.getToken();
		 String _token = tokenService.getSysToken(clientToken);
		 if (StringUtils.isBlank(_token)) {
        	throw new BusinessException("gateway01");
		 }
	}
	
	private void checkSign(Header header, String body,Partner partner) {
		String sign = header.getSign();
		Map<String, String> config = partner.getConfig();
		boolean verifySign = PortalPolicyHelper.verifySign(body, sign,config.get("sign.alg"),config.get("sign.key"));
		if (!verifySign) throw new BusinessException("gateway00");
	}
	
	private BaseRequestBody buildRequestBody(Header header, String body){
        Long partnerId = header.getPartnerId();

		
        BaseRequestBody baseRequestBody = JSON.parseObject(body, BaseRequestBody.class);
        baseRequestBody.setContent(body);
        String serviceCode = baseRequestBody.getServiceName();
        String traceNO = baseRequestBody.getTraceNo();
        String operType = baseRequestBody.getOperType();
        
        Long checkRepeatRequestSeconds = Long.parseLong(this.checkRepeatRequestSeconds);
        if(!StringUtils.isBlank(traceNO)) {
	        boolean isRepeat = MessageFormatUtils.checkRepeatRequest(redisTemplate, GATEWAY_INTERACT_REQUEST_KEY, checkRepeatRequestSeconds,
	                partnerId, serviceCode, traceNO,operType);
	        if (isRepeat) throw new BusinessException("gateway21", serviceCode, traceNO,operType);
        }
        
        //check no record before.
        if(operType != null && operType.equalsIgnoreCase("ADD")) {
        	List<Interact> interacts = interactRpt.findInteract(partnerId, serviceCode, traceNO, operType);
        	if(interacts != null && interacts.size() >=1) {
        		throw new BusinessException("gateway22", serviceCode, traceNO,operType);
        	}
        }
        
        return baseRequestBody;
    }

	
}
