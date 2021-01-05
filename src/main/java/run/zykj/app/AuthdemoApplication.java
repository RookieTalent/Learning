package run.zykj.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("run.zykj.app")
public class AuthdemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
