package run.zykj.app;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthdemoApplicationTests {

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Autowired
    private StringEncryptor demoStringEncryptor;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void test1(){
        String encrypt = demoStringEncryptor.encrypt(password);
        System.out.println("encrypt = " + encrypt);
    }

    @Test
    void test2(){
        String obj = (String) redisTemplate.opsForValue().get("sys_config:sys.index.skinName");

        System.out.println("obj.length() = " + obj.length());
    }
}
