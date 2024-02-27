package com.deqiying.framework.annotation;

import com.deqiying.framework.config.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用redis相关bean
 *
 * @author qiying
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisConfig.class})
public @interface EnableRedis {
}
