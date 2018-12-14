package cn.freeexchange.gateway.domain.partner;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import cn.freeexchange.common.base.usertype.StringMapUserType;
import cn.freeexchange.gateway.domain.BaseRequestBody;
import cn.freeexchange.gateway.domain.interact.Header;
import cn.freeexchange.gateway.domain.interact.Interact;
import cn.freeexchange.gateway.domain.service.Service;
import cn.freeexchange.gateway.dto.PartnerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//接入方
@Entity
@Table(name = "tb_partner", schema = "gateway")
@Getter
@Setter
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "stringMap", typeClass = StringMapUserType.class)})
public class Partner implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1355563832405232059L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    @Column(name = "update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

    @Column(name = "logic_delete")
    private Boolean logicDelete = false;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parent_id")
    private Partner parent;
    
    @ManyToMany
    @JoinTable(name = "tb_partner_service",schema="gateway",
    	joinColumns=@JoinColumn(name="partner_id"),
    	inverseJoinColumns=@JoinColumn(name="service_id")
    )
    @MapKeyColumn(name="code")
    private Map<String, Service> selfServices;	//商户支持的服务
    
    @Column(name = "config")
    @Type(type = "stringMap")
    private Map<String, String> config;
    
    
    @Column(name="name")
    private String name;
    
    //private SupportElements ips;	//支持ip列表
    
	public PartnerDTO makeDTO() {
		PartnerDTO partnerDto = new PartnerDTO();
		partnerDto.setPartnerId(id);
		partnerDto.setToken(null);
		partnerDto.setTikectNo(null);
		//partnerDto.setSignatureKey(config.getSignatureKey());
		//partnerDto.setCipherKey(cipher.getCipherKey());
		return partnerDto;
	}
	
	public Header makeHeader(String body) {
        return new Header(PortalPolicyHelper.calcSign(body,config.get("sign.alg"),config.get("sign.key")));
    }
	
	public Interact createInteract(Header header, BaseRequestBody requestBody, String ip) {
        //checkIp(ip);
        
        String serviceCode = requestBody.getServiceName();
        String operType = requestBody.getOperType();
        Service service = selfServices.get(serviceCode);
        return new Interact(this, service, serviceCode,operType, ip, header, requestBody);
    }
	
	
	public String calcSgin(String plaintext) {
        return PortalPolicyHelper.calcSign(plaintext,config.get("sign.alg"),config.get("sign.key"));
    }
	
    
}
