package run.zykj.app;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import run.zykj.app.subject13.Text;
import run.zykj.app.subject13.subject13;
import run.zykj.app.subject14.service.MailService;
import run.zykj.app.subject14.utils.VerityCodeUtils;
import run.zykj.app.subject18.Friend;
import run.zykj.app.subject22.mapper.MusicTableMapper;
import run.zykj.app.subject25.utils.StbStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthdemoApplicationTests {
    private final static String RAND_FRIEND = "rand_friend";
    private final static String MUSIC_RANK = "music_rank";
    private final static Integer limit = 3;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MailService mailService;

    @Autowired
    private MusicTableMapper musicTableMapper;

    @Test
    void test1(){
        Set<Object> range = redisTemplate.opsForZSet().range(MUSIC_RANK, 0, 3);
        for (Object o : range) {
            System.out.println("o = " + o);
        }
    }

    @Test
    void test2(){
        Long userId = 103L;
        // 编写content内容
        StringBuilder sb = new StringBuilder();
        // 生成code
        String code = VerityCodeUtils.generateVerifyCode(6);
        // 需要生成短链接 放入redis 可能还需要code
        sb.append("<h1>此邮件为官方激活邮件!</h1>");
        sb.append("<h3><a href='http://localhost:8085/user/activation/");
        sb.append(code);sb.append("/");
        sb.append(userId);
        sb.append("'></a></h3>");

        System.out.println("sb.toString() = " + sb.toString());
    }

    @Test
    void test3(){
        Text text = new Text();text.setId("1");text.setTitle("标题");text.setContent("内容");
        Text text2 = new Text();text.setId("2");text.setTitle("标题2");text.setContent("内容2");
        Text text3 = new Text();text.setId("3");text.setTitle("标题3");text.setContent("内容3");

        redisTemplate.opsForList().leftPush(subject13.THINGS+text.getId(), text);
        redisTemplate.opsForList().leftPush(subject13.THINGS+text2.getId(), text2);
        redisTemplate.opsForList().leftPush(subject13.THINGS+text3.getId(), text3);
    }

    @Test
    void test4(){
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

        List<Object> objects = redisTemplate.opsForSet().randomMembers(RAND_FRIEND, 3);
        System.out.println("objects = " + objects);
    }

    @Test
    void test5(){
        String word = "nba";
        List<String> list = StbStringUtils.subStringList(word);
        for (String s : list) {
            System.out.println("s = " + s);
        }
    }

}
