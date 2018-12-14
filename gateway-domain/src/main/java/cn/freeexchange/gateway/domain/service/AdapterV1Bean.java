package cn.freeexchange.gateway.domain.service;


import static cn.freeexchange.gateway.domain.service.ServiceHandler.TYPE_V1_BEAN;

import java.io.Serializable;

import cn.freeexchange.common.base.ann.ClassNum;
import cn.freeexchange.gateway.dto.ContextDTO;

/**
 * Created by lindongcheng on 16/5/31.
 */
@ClassNum(superClass = ServiceHandler.class,value = TYPE_V1_BEAN)
public class AdapterV1Bean implements ServiceHandler,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5103730564004011151L;
	
	private String beanName;
    //private ServiceAdapter adapter;

    public AdapterV1Bean(String beanName) {
        this.beanName = beanName;
        //adapter = queryBean(beanName, ServiceAdapter.class);
    }

    @Override
    public String exec(ContextDTO contextDTO, String requestBody) {
        //return adapter.doService(contextDTO.getPartnerDTO(), requestBody);
    	return null;
    }

    @Override
    public String getFlag() {
        return beanName;
    }
}
