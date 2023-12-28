package com.commerce.core.common.config;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class AppTestConfig {

    /**
     * Redis 사용 안함
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        return null;
    }
}
