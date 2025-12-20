package com.authh.springjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
(scanBasePackages = {"com.authh.springjwt"})
public class CombinedApplication {
    public static void main(String[] args) {
        SpringApplication.run(CombinedApplication.class, args);
    }
}

