package com.commerce.core.member.service;

import com.commerce.core.member.entity.Member;
import com.commerce.core.member.vo.MemberDto;

import java.util.Optional;

public interface MemberService {

    Member createMember(MemberDto dto);
    Optional<Member> selectMember(Long memberSeq);
}
