package cn.freeexchange.gateway.service.utils;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 */
public class MessageFormatUtils {

    public static String text(String pattern, Object... arguments) {
        if (StringUtils.isBlank(pattern)) return null;
        return MessageFormat.format(pattern, arguments);
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean checkRepeatRequest(RedisTemplate redisTemplate, String _key, long timeout, Object... arguments){
        if(timeout <= 0) return false;
        String key = text(_key, arguments);
        Boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = key.getBytes();
                Boolean reslutFlag = connection.setNX(bytes, "OK".getBytes());
                if (reslutFlag) connection.expire(bytes, timeout);
                return reslutFlag;
            }
        });
        return !result;
    }
}
