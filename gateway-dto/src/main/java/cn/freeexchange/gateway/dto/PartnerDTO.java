package cn.freeexchange.gateway.dto;

import java.io.Serializable;

public class PartnerDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -314948873587618021L;
	//签名秘钥
	private String signatureKey;
	//加密秘钥
	private String cipherKey;
	
	//令牌
	private String token;
	//票据号
	private String tikectNo;
	
	
	private Long partnerId;


	public String getSignatureKey() {
		return signatureKey;
	}


	public void setSignatureKey(String signatureKey) {
		this.signatureKey = signatureKey;
	}


	public String getCipherKey() {
		return cipherKey;
	}


	public void setCipherKey(String cipherKey) {
		this.cipherKey = cipherKey;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getTikectNo() {
		return tikectNo;
	}


	public void setTikectNo(String tikectNo) {
		this.tikectNo = tikectNo;
	}


	public Long getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

}
