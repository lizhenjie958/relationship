package com.mcf.relationship.infra.cache;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 内部基础缓存使用Memcache来做
 * Redis只使用其高级特性：使用Redisson和BitMap
 * 项目高QPS的请求缓存需要搭配CaffeineUtil使用，根据业务组合使用
 *
 * @Author ZhuPo
 * @date 2026/1/31 13:38
 */
@Slf4j
@Repository
public class MemcachedRepository {
    @Resource
    private MemcachedClient memcachedClient;

    @Value("${memcached.expire-time:7200}")
    private int defaultExpire;

    /**
     * 设置缓存
     */
    public boolean set(String key, Object value, int expire) {
        try {
            return memcachedClient.set(key, expire, value);
        } catch (Exception e) {
            log.error("Memcached set error, key: {}", key, e);
            return false;
        }
    }

    public boolean set(String key, Object value) {
        return set(key, value, defaultExpire);
    }

    /**
     * 获取缓存
     */
    public <T> T get(String key) {
        try {
            return memcachedClient.get(key);
        } catch (Exception e) {
            log.error("Memcached get error, key: {}", key, e);
            return null;
        }
    }

    /**
     * 删除缓存
     */
    public boolean delete(String key) {
        try {
            return memcachedClient.delete(key);
        } catch (Exception e) {
            log.error("Memcached delete error, key: {}", key, e);
            return false;
        }
    }

    /**
     * 原子递增
     */
    public long incr(String key, long delta, long initValue) {
        try {
            return memcachedClient.incr(key, delta, initValue);
        } catch (Exception e) {
            log.error("Memcached incr error, key: {}", key, e);
            return -1;
        }
    }

    /**
     * 原子递减
     */
    public long decr(String key, long delta, long initValue) {
        try {
            return memcachedClient.decr(key, delta, initValue);
        } catch (Exception e) {
            log.error("Memcached decr error, key: {}", key, e);
            return -1;
        }
    }

    /**
     * 检查key是否存在
     */
    public boolean exists(String key) {
        try {
            // XMemcached 没有直接的exists方法，用get判断
            return memcachedClient.get(key) != null;
        } catch (Exception e) {
            log.error("Memcached exists error, key: {}", key, e);
            return false;
        }
    }
}
