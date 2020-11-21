package kr.chis.springbootwebjpaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootWebJpaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebJpaApiApplication.class, args);
    }

}
