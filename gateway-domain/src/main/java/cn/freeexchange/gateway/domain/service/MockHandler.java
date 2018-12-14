package cn.freeexchange.gateway.domain.service;


import static cn.freeexchange.gateway.domain.service.ServiceHandler.TYPE_MOCK;

import java.io.Serializable;

import cn.freeexchange.common.base.ann.ClassNum;
import cn.freeexchange.gateway.dto.ContextDTO;
/**
 */
@ClassNum(superClass = ServiceHandler.class,value = TYPE_MOCK)
public class MockHandler implements ServiceHandler,Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5933802570102243314L;
	
	private String response;

    public MockHandler(String response) {
        this.response = response;
    }

    @Override
    public String exec(ContextDTO contextDTO, String requestBody) {
        return response;
    }

    @Override
    public String getFlag() {
        return response;
    }
}
