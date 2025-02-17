package com.commerce.core.point.repository;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.point.entity.MemberPoint;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface PointRepository extends JpaRepository<MemberPoint, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<MemberPoint> findByMember(Member member);
    Optional<MemberPoint> findByMember_MemberSeq(Long memberSeq);
}
