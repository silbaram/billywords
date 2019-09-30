package com.billywords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class BillywordsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillywordsApplication.class, args);
    }

}
