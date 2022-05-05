package com.razdeep.konsignapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class KonsignApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KonsignApiApplication.class, args);
    }

}
