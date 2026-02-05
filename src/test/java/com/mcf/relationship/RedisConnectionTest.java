package com.mcf.relationship;

import com.mcf.relationship.infra.manager.RedisManager;
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
    private RedisManager redisService;

    @Test
    void testRedisConnection() {
        // 测试基本存取
        redisService.setString("test:key", "Hello Redis", 6, TimeUnit.MINUTES);
        Object value = redisService.get("test:key");

        System.err.println(value);
        System.out.println("✅ Redis 连接与操作测试通过！");
    }
}