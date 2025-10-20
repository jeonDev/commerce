package com.commerce.core.point.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.point.domain.PointDao;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.type.PointProcessStatus;
import lombok.extern.slf4j.Slf4j;
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
    public MemberPoint pointAdjustment(Long memberSeq,
                                       Long point,
                                       PointProcessStatus pointProcessStatus
    ) {
        log.info("[Point] 포인트 조정 (고객번호 : {} 조정금액 {}[{}])", memberSeq, point, point);

        // 1. Member Use Find
        var member = memberDao.findByUsingMemberSeq(memberSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Existing Point Find & Point Setting
        var memberPoint = pointDao.findByMemberSeqForUpdate(member.getMemberSeq())
                .orElseGet(() -> MemberPoint.builder()
                        .memberSeq(member.getMemberSeq())
                        .point(0L)
                        .build()
                );
        memberPoint.pointAdjustment(point, pointProcessStatus);
        pointDao.memberPointSave(memberPoint);

        // 3. History Save
        pointDao.pointHistorySave(memberPoint, point, pointProcessStatus);

        return memberPoint;
    }

}
