package cn.freeexchange.gateway.domain;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.freeexchange.common.base.exception.BusinessException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseRequestBody {
	
	private String serviceName;
	private String operType;
	private String notifyUrl;
	
    private String traceNo;
    private Map<String,List<String>> cryptoFields;
    
    private String content;
    
    
    
    public void verify() {
    	if(StringUtils.isBlank(serviceName)) {
			throw new BusinessException("gateway10", "serviceName");
		}
    	if(StringUtils.isBlank(operType)) {
			throw new BusinessException("gateway10", "operType");
		}
    	if(StringUtils.isBlank(notifyUrl)) {
			throw new BusinessException("gateway10", "notifyUrl");
		}
    	if(StringUtils.isBlank(traceNo)) {
			throw new BusinessException("gateway10", "traceNo");
		}
    }
}
