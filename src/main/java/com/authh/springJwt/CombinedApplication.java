package com.authh.springJwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
(scanBasePackages = {"com.authh.springJwt"})
public class CombinedApplication {
    public static void main(String[] args) {
        SpringApplication.run(CombinedApplication.class, args);
    }
}

