package com.commerce.core.service.member;

import com.commerce.core.entity.Member;
import com.commerce.core.vo.member.MemberDto;

public interface MemberService {

    Member createMember(MemberDto dto);
    Member selectMember(Long memberSeq);
}
