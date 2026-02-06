package com.mcf.relationship;

import com.mcf.relationship.infra.cache.RedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhuPo
 * @date 2026/2/5 15:26
 */
@SpringBootTest
class RedisConnectionTest {

    @Autowired
    private RedisRepository redisRepository;

    @Test
    void testRedisConnection() {
        // 测试基本存取
        redisRepository.setString("test:key", "Hello Redis", 6, TimeUnit.MINUTES);
        Object value = redisRepository.get("test:key");

        System.err.println(value);
        System.out.println("✅ Redis 连接与操作测试通过！");
    }
}