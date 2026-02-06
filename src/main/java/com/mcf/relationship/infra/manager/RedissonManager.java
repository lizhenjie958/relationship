package com.mcf.relationship.infra.manager;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redisson分布式锁管理器
 *
 * @author ZhuPo
 * @date 2026/2/5 15:50
 */
@Component
@Slf4j
public class RedissonManager {


    @Resource
    private RedissonClient redissonClient;

    /**
     * 尝试获取分布式锁
     * @param lockName 锁名称
     * @param waitTime 等待时间(秒)
     * @param leaseTime 持有时间(秒)
     * @return 是否获取成功
     */
    public boolean tryLock(String lockName, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock("distributed:lock:" + lockName);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 释放锁
     */
    public void unlock(String lockName) {
        RLock lock = redissonClient.getLock("distributed:lock:" + lockName);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
