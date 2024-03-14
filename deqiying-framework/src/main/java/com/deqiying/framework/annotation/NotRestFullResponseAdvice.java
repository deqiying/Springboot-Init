package com.deqiying.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不启用自动封装响应体注解
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 17:54
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotRestFullResponseAdvice {
}
