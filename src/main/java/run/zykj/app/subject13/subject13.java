package run.zykj.app.subject13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author xiaolin
 * @date 2021/1/6 10:46
 */
public class subject13 {

    public final static String THINGS = "things:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    void test(){
        Text text = new Text();text.setId("1");text.setTitle("标题");text.setContent("内容");
        Text text2 = new Text();text.setId("2");text.setTitle("标题2");text.setContent("内容2");
        Text text3 = new Text();text.setId("3");text.setTitle("标题3");text.setContent("内容3");

        redisTemplate.opsForList().leftPush(THINGS+text.getId(), text);
        redisTemplate.opsForList().leftPush(THINGS+text2.getId(), text2);
        redisTemplate.opsForList().leftPush(THINGS+text3.getId(), text3);
    }

}
