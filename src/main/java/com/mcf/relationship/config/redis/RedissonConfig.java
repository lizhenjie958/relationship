package com.mcf.relationship.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhuPo
 * @date 2026/2/5 15:43
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    @Value("${spring.data.redis.database:0}")
    private int database;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();

        // 单节点模式
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%d", redisHost, redisPort))
                .setDatabase(database)
                .setPassword(redisPassword.isEmpty() ? null : redisPassword)
                .setConnectionPoolSize(64)
                .setConnectionMinimumIdleSize(24)
                .setSubscriptionConnectionPoolSize(50)
                .setConnectTimeout(10000)
                .setTimeout(3000)
                .setRetryAttempts(3)
                .setRetryInterval(1500);

        // 序列化配置
        config.setCodec(new JsonJacksonCodec());

        return Redisson.create(config);
    }

    @Bean
    public RedissonProperties redissonProperties() {
        return new RedissonProperties();
    }
}
