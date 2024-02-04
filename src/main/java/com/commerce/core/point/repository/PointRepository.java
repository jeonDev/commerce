package com.commerce.core.point.repository;

import com.commerce.core.member.entity.Member;
import com.commerce.core.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByMember(Member member);
    Optional<Point> findByMember_MemberSeq(Long memberSeq);
}
