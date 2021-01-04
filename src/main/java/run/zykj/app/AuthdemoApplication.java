package run.zykj.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("run.zykj.app.mapper")
public class AuthdemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
