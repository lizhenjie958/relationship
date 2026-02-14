package com.mcf.relationship.infra.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.mcf.relationship.common.enums.CacheEnum;
import com.mcf.relationship.common.function.DBQuery;
import com.mcf.relationship.common.function.DBUpdate;
import com.mcf.relationship.common.util.CaffeineUtil;
import com.mcf.relationship.infra.cache.MemcachedRepository;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author ZhuPo
 * @date 2026/2/10 16:43
 */
@Component
public class SecondaryCacheManager {

    @Resource
    private MemcachedRepository memcachedRepository;

    /**
     * 获取缓存
     * @param cacheEnum
     * @param key
     * @param dbQuery
     * @return
     * @param <T>
     */
    public <T> T getByCache(CacheEnum cacheEnum, String key, DBQuery<T> dbQuery){
        Cache<String, T> cache = CaffeineUtil.getCache(cacheEnum);
        return cache.get(key, s -> {
            String memcachedKey = cacheEnum.buildKey(key);
            T redisValue = memcachedRepository.get(memcachedKey);
            if (redisValue != null) {
                return redisValue;
            }
            T query4DB = dbQuery.query();
            if (query4DB != null) {
                memcachedRepository.set(memcachedKey, query4DB);
            }
            return query4DB;
        });
    }



    /**
     * 删除缓存
     * @param cacheEnum
     * @param key
     * @param <T>
     */
    public <T> Boolean delCache(CacheEnum cacheEnum, String key, DBUpdate dbUpdate){
        boolean update = dbUpdate.update();
        if (update) {
            Cache<String, T> cache = CaffeineUtil.getCache(cacheEnum);
            String memcachedKey = cacheEnum.buildKey(key);
            memcachedRepository.delete(memcachedKey);
            cache.invalidate(key);
        }
        return update;
    }
}
