package run.zykj.app.subject18;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author xiaolin
 * @date 2021/1/6 11:28
 */
@Slf4j
public class Weibo {

    private final static String RAND_FRIEND = "rand_friend";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    void init(){
        log.info("数据初始化中...");

        Friend friend1 = new Friend();friend1.setId(1L);friend1.setName("胡1");
        Friend friend2 = new Friend();friend2.setId(2L);friend2.setName("胡2");
        Friend friend3 = new Friend();friend3.setId(3L);friend3.setName("胡3");
        Friend friend4 = new Friend();friend4.setId(4L);friend4.setName("胡4");
        Friend friend5 = new Friend();friend5.setId(5L);friend5.setName("胡5");

        redisTemplate.opsForSet().add(RAND_FRIEND, friend1);
        redisTemplate.opsForSet().add(RAND_FRIEND, friend2);
        redisTemplate.opsForSet().add(RAND_FRIEND, friend3);
        redisTemplate.opsForSet().add(RAND_FRIEND, friend4);
        redisTemplate.opsForSet().add(RAND_FRIEND, friend5);
    }

    void impl(){
        List<Object> objects = redisTemplate.opsForSet().randomMembers(RAND_FRIEND, 3);
        System.out.println("objects = " + objects);
    }
}
