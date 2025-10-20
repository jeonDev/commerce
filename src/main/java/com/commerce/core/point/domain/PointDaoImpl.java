package com.commerce.core.point.domain;

import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.domain.entity.PointHistory;
import com.commerce.core.point.domain.repository.PointHistoryRepository;
import com.commerce.core.point.domain.repository.PointRepository;
import com.commerce.core.point.type.PointProcessStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PointDaoImpl implements PointDao {

    private final PointRepository pointRepository;

    private final PointHistoryRepository pointHistoryRepository;

    public PointDaoImpl(PointRepository pointRepository,
                        PointHistoryRepository pointHistoryRepository) {
        this.pointRepository = pointRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public Optional<MemberPoint> findByMemberSeqForUpdate(Long memberSeq) {
        return pointRepository.findByMemberSeqForUpdate(memberSeq);
    }

    @Override
    public MemberPoint memberPointSave(MemberPoint point) {
        return pointRepository.save(point);
    }

    @Override
    public PointHistory pointHistorySave(MemberPoint memberPoint, Long point, PointProcessStatus pointProcessStatus) {
        return pointHistoryRepository.save(memberPoint.generateHistoryEntity(point, pointProcessStatus));
    }

    @Override
    public Page<PointHistory> findByMemberPaging(Pageable pageable, Long memberSeq) {
        return pointHistoryRepository.findByMemberSeq(pageable, memberSeq);
    }

    @Override
    public Optional<MemberPoint> findByMemberSeq(Long memberSeq) {
        return pointRepository.findByMemberSeq(memberSeq);
    }
}
