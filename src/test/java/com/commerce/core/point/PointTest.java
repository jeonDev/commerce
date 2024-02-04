package com.commerce.core.point;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.point.entity.Point;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class PointTest {
    final int COUPON_THREAD_COUNT = 10;
    @Autowired
    private PointService pointService;

    @Test
    @Order(1)
    void pointCharge_success() {
        PointDto pointDto = new PointDto();
        pointDto.setMemberSeq(1L);
        pointDto.setPoint(10000L);
        Point originPoint = pointService.selectPoint(pointDto.getMemberSeq()).get();
        pointService.charge(pointDto);

        Point point = pointService.selectPoint(pointDto.getMemberSeq()).get();
        assertThat(point.getPoint()).isEqualTo(pointDto.getPoint() + originPoint.getPoint());
    }

    @Test
    @Order(2)
    void pointPayment_fail() {

        PointDto pointDto = new PointDto();
        pointDto.setMemberSeq(1L);
        Point originPoint = pointService.selectPoint(pointDto.getMemberSeq()).get();
        pointDto.setPoint(originPoint.getPoint() + 1L);
        assertThrows(CommerceException.class, () -> {
            pointService.withdraw(pointDto);
        });

    }

    @Test
    @Order(3)
    void pointPayment_success() {
        PointDto pointDto = new PointDto();
        pointDto.setMemberSeq(1L);
        pointDto.setPoint(10000L);
        Point originPoint = pointService.selectPoint(pointDto.getMemberSeq()).get();

        pointService.withdraw(pointDto);
        Point point = pointService.selectPoint(pointDto.getMemberSeq()).get();
        assertThat(point.getPoint()).isEqualTo(originPoint.getPoint() - pointDto.getPoint());
    }

    @Test
    void point_lock_test() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(COUPON_THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(COUPON_THREAD_COUNT);

        for(int i = 0; i < COUPON_THREAD_COUNT; i++) {
            PointDto pointDto = new PointDto();
            pointDto.setMemberSeq(2L);
            pointDto.setPoint(1000L);
            int cnt = i;
            executorService.execute(() -> {
                try {
                    if(cnt % 2 == 0) pointService.charge(pointDto);
                    else pointService.withdraw(pointDto);
                } catch (Exception e) {
                    log.error("error : {}", e);
                }
                latch.countDown();
            });
        }

        latch.await();
    }
}
