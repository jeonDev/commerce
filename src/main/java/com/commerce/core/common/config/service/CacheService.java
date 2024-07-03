package com.commerce.core.common.config.service;

public interface CacheService<K, V> {

    void setCache(K key, V value);

    V getCache(K key);

    void deleteCache(K key);

}
