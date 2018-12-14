package cn.freeexchange.gateway.domain.service;

import static cn.freeexchange.gateway.domain.service.ServiceHandler.TYPE_V2_BEAN;

import java.io.Serializable;

import cn.freeexchange.common.base.ann.ClassNum;
import cn.freeexchange.gateway.dto.ContextDTO;

/**
 * Created by lindongcheng on 16/5/31.
 */
@ClassNum(superClass = ServiceHandler.class,value = TYPE_V2_BEAN)
public class AdapterV2Bean implements ServiceHandler,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1602163186477354316L;
	private String beanName;
    //private ServiceAdapterV2 adapter;

    public AdapterV2Bean(String beanName) {
        this.beanName = beanName;
        //adapter = queryBean(beanName, ServiceAdapterV2.class);
    }

    @Override
    public String exec(ContextDTO contextDTO, String requestBody) {
        return null;
    }

    @Override
    public String getFlag() {
        return beanName;
    }
}
