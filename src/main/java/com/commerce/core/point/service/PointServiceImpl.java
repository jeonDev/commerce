package com.commerce.core.point.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.point.domain.PointDao;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.domain.entity.PointHistory;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;
import com.commerce.core.point.vo.PointProcessStatus;
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
public class PointServiceImpl implements PointService {

    private final PointDao pointDao;
    private final MemberService memberService;

    @Transactional
    @Override
    public PointAdjustmentServiceResponse pointAdjustment(PointAdjustmentServiceRequest request) {
        // 1. Member Use Find
        Member member = memberService.selectUseMember(request.memberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Existing Point Find & Point Setting
        Optional<MemberPoint> optionalPoint = pointDao.findByMember(member);
        MemberPoint point;
        if(optionalPoint.isPresent()) {
            point = optionalPoint.get();
            point.pointChange(request.point(), request.pointProcessStatus());
        } else {
            if(request.pointProcessStatus() == PointProcessStatus.PAYMENT) throw new CommerceException(ExceptionStatus.POINT_LACK);

            point = MemberPoint.builder()
                    .member(member)
                    .point(request.point())
                    .build();
        }

        // 3. Entity And History Save
        this.entitySaveAndHistoryGenerate(point, request);

        return PointAdjustmentServiceResponse.builder()
                .memberSeq(member.getMemberSeq())
                .point(request.point())
                .balancePoint(point.getPoint())
                .build();
    }

    private void entitySaveAndHistoryGenerate(MemberPoint point, PointAdjustmentServiceRequest request) {
        pointDao.memberPointSave(point);
        pointDao.pointHistorySave(point.generateHistoryEntity(request.point(), request.pointProcessStatus()));
    }

    @Transactional(readOnly = true)
    @Override
    public PageListVO<PointAdjustmentServiceResponse> selectPointHistory(int pageNumber, int pageSize, Long memberSeq) {
        Member member = memberService.selectMember(memberSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PointHistory> list = pointDao.findByMemberPaging(pageable, member);

        return PageListVO.<PointAdjustmentServiceResponse>builder()
                .list(list.getContent().stream()
                        .map(PointHistory::entityToResponse)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MemberPoint> selectPoint(Long memberSeq) {
        return pointDao.findByMemberSeq(memberSeq);
    }

}
