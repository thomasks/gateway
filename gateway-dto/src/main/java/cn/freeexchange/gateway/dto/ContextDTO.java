package cn.freeexchange.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContextDTO {
	
	private PartnerDTO partnerDTO;
    private String serviceCode;
    private String baas;
    private String refId;
    private String traceNo;
    private String notifyUrl;
    private String token;
    
    public Long getPartnerId() {
        return partnerDTO.getPartnerId();
    }

}
