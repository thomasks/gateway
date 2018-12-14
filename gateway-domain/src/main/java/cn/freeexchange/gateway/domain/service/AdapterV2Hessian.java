package cn.freeexchange.gateway.domain.service;


import static cn.freeexchange.gateway.domain.service.ServiceHandler.TYPE_V2_HESSIAN;

import java.io.Serializable;

import cn.freeexchange.common.base.ann.ClassNum;
import cn.freeexchange.gateway.dto.ContextDTO;
/**
 * Created by lindongcheng on 16/5/31.
 */
@ClassNum(superClass = ServiceHandler.class,value = TYPE_V2_HESSIAN)
public class AdapterV2Hessian implements ServiceHandler,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8216986762071785948L;
	
	private String url;
    //private ServiceAdapterV2 adapter;

    public AdapterV2Hessian(String url) {
        this.url = url;
        /*HessianProxyFactory factory = new HessianProxyFactory();
        try {
            this.adapter = (ServiceAdapterV2) factory.create(ServiceAdapterV2.class, url);
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
