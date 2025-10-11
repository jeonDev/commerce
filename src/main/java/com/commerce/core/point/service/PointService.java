package com.commerce.core.point.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.point.domain.PointDao;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PointService {

    private final PointDao pointDao;
    private final MemberDao memberDao;

    public PointService(PointDao pointDao,
                        MemberDao memberDao) {
        this.pointDao = pointDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public PointAdjustmentServiceResponse pointAdjustment(PointAdjustmentServiceRequest request) {
        log.info("[Point] 포인트 조정 (고객번호 : {} 조정금액 {}[{}])", request.memberSeq(), request.point(), request.pointProcessStatus());

        // 1. Member Use Find
        var member = memberDao.findByUsingMemberSeq(request.memberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Existing Point Find & Point Setting
        var memberPoint = pointDao.findByMember(member)
                .orElseGet(() -> MemberPoint.builder()
                        .member(member)
                        .point(0L)
                        .build()
                );
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

    @Transactional(readOnly = true)
    public PageListResponse<PointAdjustmentServiceResponse> selectPointHistory(int pageNumber, int pageSize, Long memberSeq) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var list = pointDao.findByMemberPaging(pageable, memberSeq);

        return PageListResponse.<PointAdjustmentServiceResponse>builder()
                .list(list.getContent().stream()
                        .map(PointAdjustmentServiceResponse::from)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }

}
