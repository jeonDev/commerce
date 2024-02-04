package com.commerce.core.point.repository;

import com.commerce.core.member.entity.Member;
import com.commerce.core.point.entity.Point;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Point> findByMember(Member member);
    Optional<Point> findByMember_MemberSeq(Long memberSeq);
}
