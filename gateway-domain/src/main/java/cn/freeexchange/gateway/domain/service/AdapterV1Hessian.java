package cn.freeexchange.gateway.domain.service;


import static cn.freeexchange.gateway.domain.service.ServiceHandler.TYPE_V1_HESSIAN;

import java.io.Serializable;

import cn.freeexchange.common.base.ann.ClassNum;
import cn.freeexchange.gateway.dto.ContextDTO;

/**
 */
@ClassNum(superClass = ServiceHandler.class,value = TYPE_V1_HESSIAN)
public class AdapterV1Hessian implements ServiceHandler,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7290834210105689773L;
	private String url;
    //private ServiceAdapter adapter;

    public AdapterV1Hessian(String url) {
        this.url = url;
        /*HessianProxyFactory factory = new HessianProxyFactory();
        try {
            this.adapter = (ServiceAdapter) factory.create(ServiceAdapter.class, url);
        } catch (MalformedURLException e) {
            toJSONString(new BaseResponseBody("gateway05", e.getMessage()));
        }*/
    }

    @Override
    public String exec(ContextDTO contextDTO, String requestBody) {
        return null;
    }

    @Override
    public String getFlag() {
        return url;
    }
}
