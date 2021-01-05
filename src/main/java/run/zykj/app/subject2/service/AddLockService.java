package run.zykj.app.subject2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author xiaolin
 * @date 2021/1/5 9:51
 */
@Service
public class AddLockService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final static String lockScript = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then  return redis.call('expire',KEYS[1],ARGV[2])  else return 0 end";
    private final static String releaseLockScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

    /**
     * 加锁实现
     * @param requestId
     * @param key
     * @param expiresTime
     * @return
     */
    public boolean lock(String requestId, String key, int expiresTime){
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(lockScript, Long.class);
        Long execute = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), requestId, String.valueOf(expiresTime));
        return execute == 1;
    }

    /**
     * 释放锁实现
     * @param key
     * @param requestId
     * @return
     */
    public boolean releaseLock(String key, String requestId){
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(releaseLockScript, Long.class);
        Long execute = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), requestId);
        return execute == 1;
    }

}
