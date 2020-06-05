package com.tvstorm.leespringbootstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class LeespringbootstudyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LeespringbootstudyApplication.class, args);
    }

}
