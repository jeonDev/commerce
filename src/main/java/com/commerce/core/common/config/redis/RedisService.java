package com.commerce.core.common.config.redis;

public interface RedisService {
    void setCache(String key, String value);

    String getCache(String key);

    void deleteCache(String key);
}
