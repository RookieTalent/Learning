package run.zykj.app;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import run.zykj.app.subject14.service.MailService;
import run.zykj.app.subject22.entity.MusicTable;
import run.zykj.app.subject22.mapper.MusicTableMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthdemoApplicationTests {

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
    }
}
