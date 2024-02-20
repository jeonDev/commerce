package com.commerce.core.point;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.point.entity.Point;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import com.commerce.core.point.vo.PointProcessStatus;
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
    final int TEST_THREAD_COUNT = 10;
    @Autowired
    private PointService pointService;

    @Test
    @Order(1)
    void pointCharge_success() {
        PointDto pointDto = PointDto.builder()
                .memberSeq(1L)
                .point(10000L)
                .pointProcessStatus(PointProcessStatus.CHARGE)
                .build();
        Long originPoint = 0L;
        try {
            originPoint = pointService.selectPoint(pointDto.getMemberSeq()).get().getPoint();
        } catch (Exception e) {

        }
        pointService.pointAdjustment(pointDto);

        Point point = pointService.selectPoint(pointDto.getMemberSeq()).get();
        assertThat(point.getPoint()).isEqualTo(pointDto.getPoint() + originPoint);
    }

    @Test
    @Order(2)
    void pointPayment_fail() {
        Long memberSeq = 1L;
        Long originPoint = 0L;
        try {
            originPoint = pointService.selectPoint(memberSeq).get().getPoint();
        } catch (Exception e) {

        }
        PointDto pointDto = PointDto.builder()
                .memberSeq(1L)
                .point(originPoint + 1L)
                .pointProcessStatus(PointProcessStatus.PAYMENT)
                .build();
        assertThrows(CommerceException.class, () -> {
            pointService.pointAdjustment(pointDto);
        });

    }

    @Test
    @Order(3)
    void pointPayment_success() {
        PointDto pointDto = PointDto.builder()
                .memberSeq(1L)
                .point(10000L)
                .pointProcessStatus(PointProcessStatus.PAYMENT)
                .build();
        Long originPoint = 0L;
        try {
            originPoint = pointService.selectPoint(pointDto.getMemberSeq()).get().getPoint();
        } catch (Exception e) {

        }

        pointService.pointAdjustment(pointDto);
        Point point = pointService.selectPoint(pointDto.getMemberSeq()).get();
        assertThat(point.getPoint()).isEqualTo(originPoint - pointDto.getPoint());
    }

    @Test
    void point_lock_test() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(TEST_THREAD_COUNT);

        for(int i = 0; i < TEST_THREAD_COUNT; i++) {
            int cnt = i;
            PointDto pointDto = PointDto.builder()
                    .memberSeq(2L)
                    .point(1000L)
                    .pointProcessStatus(cnt % 2 == 0 ? PointProcessStatus.CHARGE : PointProcessStatus.PAYMENT)
                    .build();
            executorService.execute(() -> {
                try {
                    pointService.pointAdjustment(pointDto);
                } catch (Exception e) {
                    log.error("error : {}", e);
                }
                latch.countDown();
            });
        }

        latch.await();
    }
}
