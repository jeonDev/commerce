package com.commerce.core.member.service;

import com.commerce.core.member.entity.Member;
import com.commerce.core.member.vo.MemberDto;

public interface MemberService {

    Member createMember(MemberDto dto);
    Member selectMember(Long memberSeq);
}
