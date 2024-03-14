package com.deqiying.framework.annotation;

import com.deqiying.framework.config.RestFullApiConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 统一响应体格式，以及接口响应异常
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 1:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestFullApiConfig.class)
public  @interface EnableRestFullApi {

}
