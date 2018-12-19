import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.freeexchange.common.base.service.RedisService;
import cn.freeexchange.common.base.utils.RediskeyUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCase {
	
	@Autowired
	RedisService redisService;

	@Test
	public void test() {
		
	}

}
