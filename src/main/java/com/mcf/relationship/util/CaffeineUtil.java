package com.mcf.relationship.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @Author ZhuPo
 * @date 2026/1/31 16:35
 */

public final class CaffeineUtil<T> {
    public final Cache<String, T> EXPIRE_ACCESS =
            Caffeine.newBuilder()
                    .expireAfterAccess(5, TimeUnit.MINUTES)
                    .maximumSize(1000)
                    .build();

    public final Cache<String, T> EXPIRE_WRITE =
            Caffeine.newBuilder()
                    .expireAfterWrite(5, TimeUnit.MINUTES)
                    .maximumSize(1000)
                    .build();
}
