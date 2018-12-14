package cn.freeexchange.gateway.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexCtrl {
	
	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "Hi, Gateway.";
	}
	
	@RequestMapping("/test")
    public String toMock() {
        return "test";
    }
}
