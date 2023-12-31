package com.commerce.core.common.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
@Order(1)
@Profile("basic")
public class RedissonLockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(com.commerce.core.common.redis.RedissonLockTarget)")
    public Object redisLockAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Redisson Proxy 호출!!");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RedissonLockTarget redisTarget = signature.getMethod().getAnnotation(RedissonLockTarget.class);
        Object result = null;
        RLock lock = redissonClient.getLock(redisTarget.value().getKey());
        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if(!available) {
                log.error("Redisson Lock 획득 실패");
                return result;
            }
            result = joinPoint.proceed();
        } finally {
            lock.unlock();
        }
        return result;
    }
}
