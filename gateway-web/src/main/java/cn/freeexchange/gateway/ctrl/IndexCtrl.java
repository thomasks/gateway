package cn.freeexchange.gateway.ctrl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.freeexchange.common.base.service.RedisService;
import cn.freeexchange.common.base.utils.RediskeyUtils;

@Controller
public class IndexCtrl {
	
	@Autowired
	RedisService redisService;
	
	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "Hi, Gateway.";
	}
	
	@RequestMapping("/test")
    public String toMock() {
        return "test";
    }
	
	@RequestMapping("/setToken")
	@ResponseBody
    public String tock() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MjM1MDI2OTA1MzAwOTkyMDAiLCJzdWIiOiJ7XCJvcGVuSWRcIjo1MjM1MDI2OTA1MzAwOTkyMDAsXCJwYXJ0bmVyXCI6MTAwMDAwfSIsImlzcyI6IkZFIiwiaWF0IjoxNTQ0OTQ2OTgxLCJleHAiOjE1NDU1NTE3ODF9.StBRQZNOMj3RjN1i4A16yQl4-0k1tPb__a5ihJt7clo";
		String redisKey = RediskeyUtils.makeTokenRedisKey("100000", "523502690530099200");
        redisService.set(redisKey, token, 604800L, TimeUnit.SECONDS);
        return "Token ,Reset.";
    }
}
