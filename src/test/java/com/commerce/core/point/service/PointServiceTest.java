package com.commerce.core.point.service;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.point.domain.PointDao;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.domain.repository.PointHistoryRepository;
import com.commerce.core.point.domain.repository.PointRepository;
import com.commerce.core.point.vo.PointDto;
import com.commerce.core.point.vo.PointProcessStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("포인트 서비스")
class PointServiceTest {
    @Mock
    PointDao pointDao;
    @Mock
    MemberService memberService;

    PointService pointService;


    @BeforeEach
    void setUp() {
        pointService = new PointServiceImpl(pointDao, memberService);
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

        MemberPoint point = MemberPoint.builder()
                .point(10000L)
                .id(1L)
                .build();
        Mockito.when(pointDao.findByMember(Mockito.any(Member.class)))
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

        MemberPoint point = MemberPoint.builder()
                .point(10000L)
                .id(1L)
                .build();
        Mockito.when(pointDao.findByMember(Mockito.any(Member.class)))
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

        Mockito.when(pointDao.findByMember(Mockito.any(Member.class)))
                .thenReturn(Optional.ofNullable(null));
        // when
        PointDto result = pointService.pointAdjustment(pointDto);
        // then
        assertThat(result.getBalancePoint()).isEqualTo(100L);
    }
}