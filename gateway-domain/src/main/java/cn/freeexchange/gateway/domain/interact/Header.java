package cn.freeexchange.gateway.domain.interact;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.common.base.usertype.StringMapUserType;
import static cn.freeexchange.common.base.req.RequestDTO.*;


@Embeddable
@TypeDefs({@TypeDef(name = "stringMap", typeClass = StringMapUserType.class)})
public class Header {
	
	@Type(type = "stringMap")
	@Column(name="content",columnDefinition="CLOB")
	private Map<String, String> content = new LinkedHashMap<>();
	
	 protected Header() {
	 }

	    public Header(String sign) {
	        content.put(HEADER_SIGN, sign);
	    }

	    public Header(Map<String, String> content) {
	        this.content.putAll(content);
	    }

	    public Long getPartnerId() {
	        return parseLong(HEADER_PARTNER_ID,content.get(HEADER_PARTNER_ID));
	    }

	    private Long parseLong(String paramName,String paramValue) {
	        try {
	            return Long.parseLong(paramValue);
	        } catch (Throwable e) {
	            throw new BusinessException("gateway26", HEADER_PARTNER_ID,paramValue);
	        }
	    }

	    public String getSign() {
	        return content.get(HEADER_SIGN);
	    }

	    public Map<String, String> getContent() {
	        return Collections.unmodifiableMap(content);
	    }

	    public String getToken() {
	        return content.get(HEADER_TOKEN);
	    }
	    
	    public String getTicketNo() {
	        return content.get(HEADER_TICKET_NO);
	    }

	    //请求头必填属性检查
	    public void checkParamMustRequired(){
	        if(isEmpty(content.get(HEADER_PARTNER_ID))) throw new BusinessException("gateway10", HEADER_PARTNER_ID);
	        if(isEmpty(content.get(HEADER_VERSION))) throw new BusinessException("gateway10", HEADER_VERSION);
	        if(isEmpty(content.get(HEADER_SIGN)) && isEmpty(content.get(HEADER_TOKEN)))
	            throw new BusinessException("gateway10", (HEADER_SIGN +"或"+ HEADER_TOKEN));
	    }
}
