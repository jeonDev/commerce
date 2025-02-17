package com.commerce.core.common.config.redis;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
//@RequiredArgsConstructor
//@Aspect
//@Component
@Order(1)
@Profile("basic")
@Deprecated
public class RedissonLockAspect {

    private static final int WAIT_TIME_SECOND = 5;

//    private final RedissonClient redissonClient;
//
//    public RedissonLockAspect(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//
//    // com.commerce.core.common.redis.RedissonLockTarget
////    @Around("@annotation(redisTarget)")
//    public Object redisLockAspect(ProceedingJoinPoint joinPoint, RedissonLockTarget redisTarget) throws Throwable {
//        log.info("Redisson Proxy 호출!!");
//
//        final String LOCK_TARGET = redisTarget.value().getKey();
//        final int LEASE_TIME = redisTarget.leaseTime();
//
//        Object result = null;
//
//        RLock lock = redissonClient.getLock(LOCK_TARGET);
//        try {
//
//            if(!lock.tryLock(WAIT_TIME_SECOND, LEASE_TIME, TimeUnit.SECONDS)) {
//                log.error("Redisson Lock 획득 실패");
//                throw new CommerceException(ExceptionStatus.LOCK_OCCUPIED_ERROR);
//            }
//
//            result = joinPoint.proceed();
//
//        } finally {
//            lock.unlock();
//        }
//        return result;
//    }
}
