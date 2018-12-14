package cn.freeexchange.gateway.domain.service;

import static cn.freeexchange.common.base.req.RequestDTO.HEADER_BAAS;
import static cn.freeexchange.common.base.req.RequestDTO.HEADER_PARTNER_ID;
import static cn.freeexchange.common.base.req.RequestDTO.HEADER_REF_ID;
import static cn.freeexchange.common.base.req.RequestDTO.HEADER_SERVICE_CODE;
import static cn.freeexchange.common.base.req.RequestDTO.HEADER_TRACE_NO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.freeexchange.common.base.ann.ClassNum;
import cn.freeexchange.common.base.req.RequestDTO;
import cn.freeexchange.common.base.utils.HttpUtils;
import cn.freeexchange.gateway.dto.ContextDTO;
import static cn.freeexchange.gateway.domain.service.ServiceHandler.TYPE_V1_HTTP;

@ClassNum(superClass = ServiceHandler.class,value = TYPE_V1_HTTP)
public class HttpHandler implements ServiceHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4692318006875945077L;
	
	private String url;
	
	public HttpHandler (String url) {
		this.url = url;
	}

	@Override
	public String exec(ContextDTO contextDTO, String requestBody) {
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put(HEADER_PARTNER_ID, contextDTO.getPartnerId().toString());
		headerMap.put(HEADER_BAAS, contextDTO.getBaas());
		headerMap.put(HEADER_SERVICE_CODE, contextDTO.getServiceCode());
		headerMap.put(HEADER_REF_ID, contextDTO.getRefId());
		headerMap.put(HEADER_TRACE_NO, contextDTO.getTraceNo());
		headerMap.put(RequestDTO.HEADER_NOTIFY_URL, contextDTO.getNotifyUrl());
		headerMap.put(RequestDTO.HEADER_TOKEN, contextDTO.getToken());
		
		return HttpUtils.sendPostJsonWithHeader(url, requestBody,headerMap);
	}

	@Override
	public String getFlag() {
		return url;
	}

}
