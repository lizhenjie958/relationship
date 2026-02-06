package com.mcf.relationship;

import com.mcf.relationship.domain.service.UserService;
import com.mcf.relationship.infra.model.UserDO;
import com.mcf.relationship.infra.cache.MemcachedRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author ZhuPo
 * @date 2026/1/31 13:42
 */
@SpringBootTest
public class RepositoryTest {

    @Resource
    private MemcachedRepository memcachedRepository;

    @Resource
    private UserService userService;

    @Test
    public void testUseMemcached() {
        memcachedRepository.set("test", "test", 600);
        String o = memcachedRepository.get("test");
        System.err.println(o);
    }

    @Test
    public void testGetUser() {
        UserDO byId = userService.getById(1L);
        System.err.println(byId);
    }
}
