package cn.freeexchange.gateway.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	
	private Long partner;
	
	private Long openId;
	
	private String nickName;
	
	private String imgUrl;
	
	
	protected MemberDto() {
		
	}
	
	public  MemberDto(Long partner,Long openId,String nickName,String imgUrl) {
		this.partner = partner;
		this.openId = openId;
		this.nickName = nickName;
		this.imgUrl = imgUrl;
	}
	
	
}
