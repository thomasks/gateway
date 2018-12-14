package cn.freeexchange.gateway.domain.interact;

import static com.alibaba.fastjson.JSON.parseObject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.freeexchange.gateway.domain.BaseRequestBody;
import cn.freeexchange.gateway.domain.BaseResponseBody;
import cn.freeexchange.gateway.domain.ResponseResult;
import cn.freeexchange.gateway.domain.partner.Partner;
import cn.freeexchange.gateway.domain.service.Service;
import cn.freeexchange.gateway.domain.usertype.ResponseResultUT;
import cn.freeexchange.gateway.dto.ContextDTO;
import cn.freeexchange.gateway.dto.PartnerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_interact", schema = "gateway")
@Setter
@Getter
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "responseResult", typeClass = ResponseResultUT.class)})
public class Interact implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 381125175440377472L;
	protected transient Logger LOGGER = LoggerFactory.getLogger(Interact.class);
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Version
    private Long version;
	
	@Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    @Column(name = "update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

    @Column(name = "logic_delete")
    private Boolean logicDelete = false;
	
    @ManyToOne
    @JoinColumn(name = "partner_id")
	private Partner partner;
    
    @ManyToOne
    @JoinColumn(name = "service_id")
	private Service service;
	
    @Column(name = "service_code")
	private String serviceCode;

	@Column(name = "opr_type")
	private String oprType;
    
	@Column(name = "trace_no")
	private String traceNo;
	
	
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "notify_url")
	private String notifyUrl;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="content",column=@Column(name="request_header",columnDefinition="CLOB"))
	})
	private Header requestHeader;
	
	@Column(name = "request_body",columnDefinition="CLOB")
	private String requestBody;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="content",column=@Column(name="response_header",columnDefinition="CLOB"))
	})
	private Header responseHeader;
	
	@Column(name = "response_body",columnDefinition="CLOB")
	private String responseBody;
	
	@Type(type = "responseResult")
	@Columns(columns= {@Column(name="response_code"),
	                      @Column(name="response_msg",columnDefinition="CLOB")})
	private ResponseResult result;
	
	public Interact(Partner partner,Service service,String serviceCode,String oprType, String ip, Header requestHeader, 
			BaseRequestBody requestBody) {
		this.partner = partner;
		this.service = service;
		this.serviceCode = serviceCode;
		this.ip = ip;
		this.oprType = oprType;
		this.requestHeader = requestHeader;
		this.requestBody = requestBody.getContent();
		//this.requestTime = requestBody.getRequestTime();
		this.traceNo = requestBody.getTraceNo();
		this.notifyUrl = requestBody.getNotifyUrl();
	}
	
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void exec() {
        LOGGER.debug("---------Gateway interact exec()方法---------");
        LOGGER.debug("接入号partnerId：" + partner.getId());
        try {
            responseBody = service.exec(makeContextDTO(), requestBody);
            LOGGER.debug("内部系统返回报文responseBody：" + responseBody);

            BaseResponseBody responseBody = parseObject(this.responseBody, BaseResponseBody.class);
            this.result = new ResponseResult(responseBody.getResponseCode(), responseBody.getResponseMsg());
        } catch (Throwable t) {
            LOGGER.debug("内部系统返回报文responseBody异常：" + t.getMessage(), t);

            this.result = new ResponseResult(t);
            this.responseBody = JSON.toJSONString(result);
        }
        this.responseHeader = partner.makeHeader(responseBody);
    }

	public Partner getPartner() {
		return partner;
	}
	
	private ContextDTO makeContextDTO() {
		ContextDTO contextDTO = new ContextDTO();
		String token = requestHeader.getToken();
		contextDTO.setToken(token);
		contextDTO.setRefId(id.toString());
		contextDTO.setServiceCode(serviceCode);
		contextDTO.setBaas(partner.getConfig().get("baas"));
		contextDTO.setTraceNo(traceNo);
		contextDTO.setNotifyUrl(notifyUrl);
		PartnerDTO partnerDTO = partner.makeDTO();
		contextDTO.setPartnerDTO(partnerDTO);
		return contextDTO;
	}
}
