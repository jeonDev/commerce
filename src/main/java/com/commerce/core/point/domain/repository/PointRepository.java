package com.commerce.core.point.domain.repository;

import com.commerce.core.point.domain.entity.MemberPoint;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface PointRepository extends JpaRepository<MemberPoint, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "5000") }) // 5초
    @Query("SELECT p FROM MemberPoint p WHERE p.memberSeq = :memberSeq")
    Optional<MemberPoint> findByMemberSeqForUpdate(Long memberSeq);
    Optional<MemberPoint> findByMemberSeq(Long memberSeq);
}
