package com.deqiying.framework.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 简单的分布式锁，基于redis
 *
 * @author deqiying
 * @since 2024/12/31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleDistributedLock {
    /**
     * 锁的Key，可以通过 SpEL 表达式动态解析
     */
    String key();

    /**
     * 锁的过期时间
     */
    int expire() default 10;

    /**
     * 锁的最小释放时间--用于避免锁释放太快
     */
    int minReleaseTime() default 0;

    /**
     * 锁的释放时间单位
     */
    TimeUnit minReleaseTimeUnit() default TimeUnit.SECONDS;

    /**
     * 锁的过期时间单位
     */
    TimeUnit expireTimeUnit() default TimeUnit.SECONDS;

    /**
     * 锁的等待时间
     */
    int waitTime() default 0;

    /**
     * 锁的等待时间单位
     */
    TimeUnit waitTimeUnit() default TimeUnit.SECONDS;

    /**
     * 锁的等待重试次数
     */
    int retry() default 0;

    /**
     * 锁的错误信息,不为空时抛异常
     */
    String errorMsg() default "";

}
