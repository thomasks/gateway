package cn.freeexchange.gateway.domain.service;

import cn.freeexchange.gateway.dto.ContextDTO;

public interface ServiceHandler {
	 int TYPE_MOCK = 0;
	 int TYPE_V1_HESSIAN = 1;
	 int TYPE_V2_HESSIAN = 2;
	 int TYPE_V1_BEAN = 3;
	 int TYPE_V2_BEAN = 4;
	 int TYPE_V1_HTTP = 5;
	 
	 String exec(ContextDTO contextDTO, String requestBody);
	 
	 String getFlag();

}
