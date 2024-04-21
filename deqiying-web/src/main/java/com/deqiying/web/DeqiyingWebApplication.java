package com.deqiying.web;

import com.deqiying.common.annotation.EnableCommon;
import com.deqiying.framework.annotation.EnableRedis;
import com.deqiying.framework.annotation.EnableRestFullApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRestFullApi
@EnableCommon
@EnableRedis
@SpringBootApplication
public class DeqiyingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeqiyingWebApplication.class, args);
    }

}
