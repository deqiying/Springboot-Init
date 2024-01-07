package com.deqiying.framework.config;


import com.deqiying.framework.advice.ControllerExceptionAdvice;
import com.deqiying.framework.advice.ControllerResponseAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通用配置类
 *
 * @author qiying
 * @version 1.0
 * @since 2024/1/7 2:11
 */
@Configuration
public class ResponseConfiguration {
    @Bean
    public ControllerResponseAdvice enableControllerResponseAdvice() {
        return new ControllerResponseAdvice();
    }
    @Bean
    public ControllerExceptionAdvice enableControllerExceptionAdvice() {
        return new ControllerExceptionAdvice();
    }
}
