package com.mcf.relationship.common.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mcf.relationship.common.enums.CacheEnum;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZhuPo
 * @date 2026/1/31 16:35
 */
public final class CaffeineUtil {


    private static final Map<Integer, Cache<String, ?>> CACHE_MAP = new ConcurrentHashMap<>();

    private CaffeineUtil(){

    }

    @SuppressWarnings("unchecked")
    public static <T> Cache<String, T> getCache(CacheEnum cacheEnum){
        return (Cache<String, T>) CACHE_MAP.computeIfAbsent(cacheEnum.getType(), key -> {
            Caffeine<Object, Object> builder = Caffeine.newBuilder()
                    .maximumSize(1000);
            builder.expireAfterWrite(5, TimeUnit.MINUTES);
            return builder.build();
        });
    }
}
