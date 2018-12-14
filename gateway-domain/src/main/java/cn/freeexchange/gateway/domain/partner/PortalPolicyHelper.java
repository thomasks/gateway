package cn.freeexchange.gateway.domain.partner;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortalPolicyHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PortalPolicyHelper.class);
	
	
	public static String calcSign(String body,String signAlg,String signKey) {
		switch (signAlg)  {
			case "SHA256":
				LOGGER.info("sha256 body is {}",body+signKey);
				String reqSign = DigestUtils.sha256Hex(body+signKey);
				LOGGER.info("body:{},signKey:{},reqSign:{}",body,signKey,reqSign);
				return reqSign;
			case "MD5":
				return DigestUtils.md5Hex(body+signKey);
			default:
				throw new UnsupportedOperationException("Unsupported Operation.");
		}
	}
	
	public static boolean verifySign(String body, String sign,String signAlg,String signKey) {
		switch (signAlg)  {
			case "SHA256":
				LOGGER.info("sha256 body is {}",body+signKey);
				String reqSign = DigestUtils.sha256Hex(body+signKey);
				LOGGER.info("body:{},signKey:{},sign:{},reqSign:{}",body,signKey,sign,reqSign);
				return reqSign.equals(sign);
			case "MD5":
				return DigestUtils.md5Hex(body+signKey).equals(sign);
			default:
				throw new UnsupportedOperationException("Unsupported Operation.");
		}
	}
	
}
