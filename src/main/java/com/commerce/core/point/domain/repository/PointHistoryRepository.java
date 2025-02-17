package com.commerce.core.point.domain.repository;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.point.domain.entity.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    Page<PointHistory> findByMember(Pageable pageable, Member member);
}
