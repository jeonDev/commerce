package com.commerce.core.point.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.point.domain.PointDao;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.domain.entity.PointHistory;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {

    private final PointDao pointDao;
    private final MemberService memberService;

    @Transactional
    public PointAdjustmentServiceResponse pointAdjustment(PointAdjustmentServiceRequest request) {
        log.info("[Point] 포인트 조정 (고객번호 : {} 조정금액 {}[{}])", request.memberSeq(), request.point(), request.pointProcessStatus());

        // 1. Member Use Find
        Member member = memberService.selectUseMember(request.memberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Existing Point Find & Point Setting
        MemberPoint memberPoint = this.getMemberPointIsNotExistsCreateMemberPoint(member);
        memberPoint.pointAdjustment(request.point(), request.pointProcessStatus());
        pointDao.memberPointSave(memberPoint);

        // 3. History Save
        pointDao.pointHistorySave(memberPoint, request.point(), request.pointProcessStatus());

        return PointAdjustmentServiceResponse.builder()
                .memberSeq(member.getMemberSeq())
                .point(request.point())
                .balancePoint(memberPoint.getPoint())
                .build();
    }

    private MemberPoint getMemberPointIsNotExistsCreateMemberPoint(Member member) {
        return pointDao.findByMember(member)
                .orElseGet(() -> MemberPoint.builder()
                        .member(member)
                        .point(0L)
                        .build()
                );
    }

    @Transactional(readOnly = true)
    public PageListResponse<PointAdjustmentServiceResponse> selectPointHistory(int pageNumber, int pageSize, Long memberSeq) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PointHistory> list = pointDao.findByMemberPaging(pageable, memberSeq);

        return PageListResponse.<PointAdjustmentServiceResponse>builder()
                .list(list.getContent().stream()
                        .map(PointAdjustmentServiceResponse::from)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<MemberPoint> selectPoint(Long memberSeq) {
        return pointDao.findByMemberSeq(memberSeq);
    }

}
