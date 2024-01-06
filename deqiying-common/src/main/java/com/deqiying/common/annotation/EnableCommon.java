package com.deqiying.common.annotation;

import com.deqiying.common.config.CommonImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 加载 common 包下需要注入的类
 *
 * @author qiying
 * @version 1.0
 * @since 2024/1/7 1:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CommonImportSelector.class)
public  @interface EnableCommon {
    boolean useCommon() default true;
}
