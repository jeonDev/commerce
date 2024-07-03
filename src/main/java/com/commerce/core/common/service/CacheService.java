package com.commerce.core.common.service;

public interface CacheService<K, V> {

    void setCache(K key, V value);

    V getCache(K key);

    void deleteCache(K key);

}
