package com.commerce.core.point.service;

import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.point.domain.PointDao;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.type.PointProcessStatus;
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
    MemberDao memberDao;

    PointService pointService;

    @BeforeEach
    void setUp() {
        pointService = new PointService(pointDao, memberDao);
        Member member = Member.builder()
                .memberSeq(1L)
                .build();
        Mockito.when(memberDao.findByUsingMemberSeq(Mockito.anyLong()))
                .thenReturn(Optional.of(member));
    }

    @Test
    @DisplayName("포인트 충전")
    void 포인트_충전_성공() {
        // given
        long memberSeq = 1L;
        long point = 100L;
        PointProcessStatus pointProcessStatus = PointProcessStatus.CHARGE;

        MemberPoint memberPoint = MemberPoint.builder()
                .point(10000L)
                .memberSeq(1L)
                .build();
        Mockito.when(pointDao.findByMemberSeqForUpdate(Mockito.any(Long.class)))
                .thenReturn(Optional.of(memberPoint));
        // when
        MemberPoint result = pointService.pointAdjustment(memberSeq, point, pointProcessStatus);
        // then
        assertThat(result.getPoint()).isEqualTo(10100L);
    }

    @Test
    @DisplayName("포인트 차감")
    void 포인트_차감_성공() {
        // given
        long memberSeq = 1L;
        long point = 100L;
        PointProcessStatus pointProcessStatus = PointProcessStatus.PAYMENT;

        MemberPoint memberPoint = MemberPoint.builder()
                .point(10000L)
                .memberSeq(1L)
                .build();
        Mockito.when(pointDao.findByMemberSeqForUpdate(Mockito.any(Long.class)))
                .thenReturn(Optional.of(memberPoint));

        // when
        MemberPoint result = pointService.pointAdjustment(memberSeq, point, pointProcessStatus);
        // then
        assertThat(result.getPoint()).isEqualTo(9900L);
    }

    @Test
    @DisplayName("포인트정보 없을 시, 충전")
    void 포인트_충전_empty_성공() {
        // given
        long memberSeq = 1L;
        long point = 100L;
        PointProcessStatus pointProcessStatus = PointProcessStatus.CHARGE;

        Mockito.when(pointDao.findByMemberSeqForUpdate(Mockito.any(Long.class)))
                .thenReturn(Optional.ofNullable(null));
        // when
        MemberPoint result = pointService.pointAdjustment(memberSeq, point, pointProcessStatus);
        // then
        assertThat(result.getPoint()).isEqualTo(100L);
    }
}