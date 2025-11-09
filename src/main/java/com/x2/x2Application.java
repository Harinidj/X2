package com.x2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class x2Application {
    public static void main(String[] args) {
        SpringApplication.run(x2Application.class, args);
        System.out.println("🚀 X² Application running on http://localhost:8080");
    }
}