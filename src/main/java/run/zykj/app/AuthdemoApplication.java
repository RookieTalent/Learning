package run.zykj.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan({"run.zykj.app.mapper", "run.zykj.app.subject14.mapper", "run.zykj.app.subject22.mapper", "run.zykj.app.subject7.mapper", ""})
public class AuthdemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
