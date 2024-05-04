package com.deqiying.framework.config;

import com.deqiying.framework.utils.RedisUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis相关bean配置类
 *
 * @author qiying
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redisson.singleServerConfig.address}")
    private String address;
    @Value("${spring.redisson.singleServerConfig.password}")
    private String password;
    @Value("${spring.redisson.singleServerConfig.database}")
    private Integer database;

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //设置key序列化
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @DependsOn("redissonClient")
    public Void redisUtils(@Autowired RedisTemplate<String, Object> redisTemplate, @Autowired RedissonClient redissonClient) {
        RedisUtils.init(redisTemplate, redissonClient);
        return null;
    }

    @Bean("redissonClient")
    @DependsOn("redisTemplate")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //用"redis://"来启用SSL连接
        singleServerConfig.setAddress(address)
                .setPassword(password)
                .setDatabase(database);
        return Redisson.create(config);
    }
}
