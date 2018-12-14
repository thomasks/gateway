package cn.freeexchange.gateway.service;

import java.util.Map;

public interface PartnerService {
	
	
	String calcSgin(Long partnerId, String requestBody);
	
	public Map<String,String> getPartnerConfig(Long partner);
	
}
