package run.zykj.app.subject12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author xiaolin
 * @date 2021/1/6 10:58
 */
public class subject12 {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    void test(){
        //TODO 事先从数据库预载过来的数据， 还需要判定分页数 起始页 然后就很简单
        String key = "key";
        redisTemplate.opsForList().range(key, 0, 10);
    }
}
