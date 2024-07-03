package com.commerce.core.common.service;

import com.commerce.core.common.config.security.vo.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service("redisService")
public class RedisServiceImpl implements CacheService<String, String> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void setCache(String key, String value) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value);
        redisTemplate.expire(key, Duration.ofDays(JwtToken.REFRESH_TOKEN.getExpiredTime()));
    }

    @Override
    public String getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }
}
