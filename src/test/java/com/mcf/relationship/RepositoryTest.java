package com.mcf.relationship;

import com.mcf.relationship.domain.service.UserService;
import com.mcf.relationship.infra.model.UserDO;
import com.mcf.relationship.infra.manager.MemcachedManager;
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
    private MemcachedManager memcachedManager;

    @Resource
    private UserService userService;

    @Test
    public void testUseMemcached() {
        memcachedManager.set("test", "test", 600);
        String o = memcachedManager.get("test");
        System.err.println(o);
    }

    @Test
    public void testGetUser() {
        UserDO byId = userService.getById(1L);
        System.err.println(byId);
    }
}
