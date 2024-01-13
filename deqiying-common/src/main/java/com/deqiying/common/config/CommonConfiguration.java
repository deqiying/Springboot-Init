package com.deqiying.common.config;


import com.deqiying.common.utils.spring.SpringUtils;
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
public class CommonConfiguration {
    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }

}
