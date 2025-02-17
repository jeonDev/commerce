package com.commerce.core.point.domain;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.domain.entity.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PointDao {
    Optional<MemberPoint> findByMember(Member member);

    MemberPoint memberPointSave(MemberPoint point);

    PointHistory pointHistorySave(PointHistory pointHistory);

    Page<PointHistory> findByMemberPaging(Pageable pageable, Member member);

    Optional<MemberPoint> findByMemberSeq(Long memberSeq);
}
