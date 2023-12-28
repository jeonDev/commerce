package com.commerce.core.service.member;

import com.commerce.core.entity.Member;

public interface MemberService {

    void createMember();
    Member selectMember(Long memberSeq);
}
