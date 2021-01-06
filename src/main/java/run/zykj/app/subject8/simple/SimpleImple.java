package run.zykj.app.subject8.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import run.zykj.app.subject8.utils.shortUrlUtils;

import java.util.UUID;

/**
 * @author xiaolin
 * @date 2021/1/6 11:07
 */
public class SimpleImple {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void simple(){
        String[] strings = shortUrlUtils.shortUrl("https://baijiahao.baidu.com/s?id=1688040583479882537&wfr=spider&for=pc");
        // 这个随机ID 在多节点的情况下
        String s = UUID.randomUUID().toString();
        // 原始点击量
        long count = 0L;
        // 随机取这里的一个url都行
        redisTemplate.opsForHash().put("long_url", "https://baijiahao.baidu.com/s?id=1688040583479882537&wfr=spider&for=pc" , s);
        redisTemplate.opsForHash().put("long_url", strings[2] , count);
        // 追踪点击
        redisTemplate.opsForHash().increment("long_url", strings[2], 1);
    }


}
