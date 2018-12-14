package cn.freeexchange.gateway.domain.service;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import cn.freeexchange.gateway.domain.usertype.ServiceHandlerUT;
import cn.freeexchange.gateway.dto.ContextDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_service", schema = "gateway")
@Setter
@Getter
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "handler", typeClass = ServiceHandlerUT.class)})
public class Service  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2912238853835804262L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    @Column(name = "update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

    @Column(name = "logic_delete")
    private Boolean logicDelete = false;
    
    @Column(name = "url",insertable=false,updatable=false)
	private String url;
    
    @Type(type = "handler")
    @Columns(columns= {@Column(name="type"),
                      @Column(name="url")})
    private ServiceHandler handler;
    
    //若支持冪等，則要求上送報文中必須包含traceNO參數
    @Column(name = "is_idempotent")
    private Boolean isIdempotent = false;//是否幂等
    
    public String exec(ContextDTO contextDTO, String requestBody) {
        return handler.exec(contextDTO,requestBody);
    }

    
}
