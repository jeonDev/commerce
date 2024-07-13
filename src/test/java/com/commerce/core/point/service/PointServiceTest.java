package com.commerce.core.point.service;

import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.point.entity.Point;
import com.commerce.core.point.repository.PointHistoryRepository;
import com.commerce.core.point.repository.PointRepository;
import com.commerce.core.point.vo.PointDto;
import com.commerce.core.point.vo.PointProcessStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("포인트 서비스")
class PointServiceTest {
    @Mock
    PointRepository pointRepository;
    @Mock
    PointHistoryRepository pointHistoryRepository;
    @Mock
    MemberService memberService;

    PointService pointService;


    @BeforeEach
    void setUp() {
        pointService = new PointServiceImpl(pointRepository,
                pointHistoryRepository, memberService);
        Member member = Member.builder()
                .memberSeq(1L)
                .build();
        Mockito.when(memberService.selectUseMember(Mockito.anyLong()))
                .thenReturn(Optional.of(member));
    }

    @Test
    @DisplayName("포인트 충전")
    void 포인트_충전_성공() {
        // given
        PointDto pointDto = PointDto.builder()
                .memberSeq(1L)
                .point(100L)
                .pointProcessStatus(PointProcessStatus.CHARGE)
                .build();

        Point point = Point.builder()
                .point(10000L)
                .id(1L)
                .build();
        Mockito.when(pointRepository.findByMember(Mockito.any(Member.class)))
                .thenReturn(Optional.of(point));
        // when
        PointDto result = pointService.pointAdjustment(pointDto);
        // then
        assertThat(result.getBalancePoint()).isEqualTo(10100L);
    }

    @Test
    @DisplayName("포인트 차감")
    void 포인트_차감_성공() {
        // given
        PointDto pointDto = PointDto.builder()
                .memberSeq(1L)
                .point(100L)
                .pointProcessStatus(PointProcessStatus.PAYMENT)
                .build();

        Point point = Point.builder()
                .point(10000L)
                .id(1L)
                .build();
        Mockito.when(pointRepository.findByMember(Mockito.any(Member.class)))
                .thenReturn(Optional.of(point));

        // when
        PointDto result = pointService.pointAdjustment(pointDto);
        // then
        assertThat(result.getBalancePoint()).isEqualTo(9900L);
    }

    @Test
    @DisplayName("포인트정보 없을 시, 충전")
    void 포인트_충전_empty_성공() {
        // given
        PointDto pointDto = PointDto.builder()
                .memberSeq(1L)
                .point(100L)
                .pointProcessStatus(PointProcessStatus.CHARGE)
                .build();

        Mockito.when(pointRepository.findByMember(Mockito.any(Member.class)))
                .thenReturn(Optional.ofNullable(null));
        // when
        PointDto result = pointService.pointAdjustment(pointDto);
        // then
        assertThat(result.getBalancePoint()).isEqualTo(100L);
    }
}