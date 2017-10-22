package com.atlas.autoLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutoLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoLoginApplication.class, args);
    }
}
