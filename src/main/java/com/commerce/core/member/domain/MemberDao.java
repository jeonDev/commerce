package com.commerce.core.member.domain;

import com.commerce.core.member.domain.dto.MemberInfoDto;
import com.commerce.core.member.domain.entity.Member;

import java.util.Optional;

public interface MemberDao {
    Member save(Member member);
    Optional<Member> findById(Long id);

    Optional<Member> findByUsingMemberSeq(Long memberSeq);

    Optional<Member> findByUsingId(String id);

    MemberInfoDto selectMemberInfo(Long memberSeq);
}
