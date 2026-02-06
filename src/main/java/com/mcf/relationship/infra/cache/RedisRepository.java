package com.mcf.relationship.infra.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhuPo
 * @date 2026/2/5 15:25
 */
@Repository
public class RedisRepository {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储字符串值
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void setString(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 存储对象（自动序列化）
     *
     * @param key     键
     * @param value   对象值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void setObject(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取值（支持泛型）
     *
     * @param key   键
     * @return 反序列化后的对象
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取值（支持泛型）
     *
     * @param key   键
     * @param clazz 返回值类型
     * @param <T>   泛型类型
     * @return 反序列化后的对象
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return clazz.cast(value);
    }

    /**
     * 删除单个键
     *
     * @param key 键
     * @return 是否删除成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除键
     *
     * @param keys 键集合
     * @return 删除成功的数量
     */
    public Long deleteBatch(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置键的过期时间
     *
     * @param key     键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 是否设置成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取键的剩余过期时间
     *
     * @param key 键
     * @return 剩余时间（秒）
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 操作 Hash 结构：插入键值对
     *
     * @param key     主键
     * @param hashKey 子键
     * @param value   值
     */
    public void putHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 操作 Hash 结构：获取子键对应的值
     *
     * @param key     主键
     * @param hashKey 子键
     * @return 值
     */
    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }
}
