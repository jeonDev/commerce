package com.commerce.core.point.domain;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.domain.entity.PointHistory;
import com.commerce.core.point.domain.repository.PointHistoryRepository;
import com.commerce.core.point.domain.repository.PointRepository;
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
    public Optional<MemberPoint> findByMember(Member member) {
        return pointRepository.findByMember(member);
    }

    @Override
    public MemberPoint memberPointSave(MemberPoint point) {
        return pointRepository.save(point);
    }

    @Override
    public PointHistory pointHistorySave(PointHistory pointHistory) {
        return pointHistoryRepository.save(pointHistory);
    }

    @Override
    public Page<PointHistory> findByMemberPaging(Pageable pageable, Member member) {
        return pointHistoryRepository.findByMember(pageable, member);
    }

    @Override
    public Optional<MemberPoint> findByMemberSeq(Long memberSeq) {
        return pointRepository.findByMember_MemberSeq(memberSeq);
    }
}
