package com.mcf.relationship.config.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ZhuPo
 * @date 2026/2/5 15:02
 */
@Configuration
public class RedisConfig {

    /**
     * 配置 RedisTemplate，设置序列化器
     * 使用 GenericJackson2JsonRedisSerializer 替代 JdkSerializationRedisSerializer，
     * 避免 JDK 版本差异导致的序列化问题，且可读性更好。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                       ObjectMapper springObjectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 配置自定义的 ObjectMapper，用于类型序列化
        ObjectMapper redisObjectMapper = springObjectMapper.copy();
        // 激活默认类型信息，以便反序列化时能识别类型
        redisObjectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        // String 序列化器用于 key
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // Jackson 序列化器用于 value
        GenericJackson2JsonRedisSerializer jsonSerializer =
                new GenericJackson2JsonRedisSerializer(redisObjectMapper);
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        // 启用事务支持（如果需要）
        template.setEnableTransactionSupport(false);

        template.afterPropertiesSet();
        return template;
    }
}
