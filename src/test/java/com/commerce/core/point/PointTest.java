package com.commerce.core.point;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.point.entity.Point;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PointTest {

    @Autowired
    private PointService pointService;

    @Test
    @Order(1)
    void pointCharge_success() {
        PointDto pointDto = new PointDto();
        pointDto.setMemberSeq(1L);
        pointDto.setPoint(10000L);
        pointService.charge(pointDto);

        Point point = pointService.selectPoint(pointDto.getMemberSeq()).get();
        assertThat(point.getPoint()).isEqualTo(pointDto.getPoint());
    }

    @Test
    @Order(2)
    void pointPayment_fail() {
        PointDto pointDto = new PointDto();
        pointDto.setMemberSeq(1L);
        pointDto.setPoint(20000L);
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
        pointService.withdraw(pointDto);
        Point point = pointService.selectPoint(pointDto.getMemberSeq()).get();
        assertThat(point.getPoint()).isEqualTo(0L);
    }
}
