package com.commerce.core.member.repository;

import com.commerce.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);

    Optional<Member> findByMemberSeqAndUseYn(Long memberSeq, String useYn);
}
