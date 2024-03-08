package com.deqiying.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置
 *
 * @author deqiying
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.http-client.pool")
public class HttpClientPoolConfig {


    /**
     * 连接池的最大连接数
     */
    private int maxTotalConnect = 300;
    /**
     * 同路由的并发数
     */
    private int maxConnectPerRoute = 60;
    /**
     * 客户端和服务器建立连接超时，默认10s
     */
    private int connectTimeout = 10 * 1000;
    /**
     * 指客户端从服务器读取数据包的间隔超时时间,不是总读取时间，默认20s
     */
    private int readTimeout = 20 * 1000;

    private String charset = StandardCharsets.UTF_8.name();
    /**
     * 重试次数,默认3次
     */
    private int retryTimes = 3;
    /**
     * 从连接池获取连接的超时时间,不宜过长,单位ms
     */
    private int connectionRequestTimout = 200;
    /**
     * 针对不同的地址,特别设置不同的长连接保持时间
     */
    private Map<String, Integer> keepAliveTargetHost = new HashMap<>();
    /**
     * 针对不同的地址,特别设置不同的长连接保持时间,单位 s
     */
    private int keepAliveTime = 60;

}

