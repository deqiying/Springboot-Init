package com.deqiying.web;

import com.deqiying.framework.annotation.EnableResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableResponse
@SpringBootApplication
public class DeqiyingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeqiyingWebApplication.class, args);
    }

}