package com.commerce.core.member.service;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.vo.MemberDto;
import com.commerce.core.member.vo.MyPageInfoDto;

import java.util.Optional;

public interface MemberService {

    Member createMember(MemberDto dto);
    Optional<Member> selectMember(Long memberSeq);
    Optional<Member> selectUseMember(Long memberSeq);
    Optional<Member> selectUseMember(String id);
    Member save(Member member);
    MyPageInfoDto selectMyInfo(Long memberSeq);
    void updateUserInfo(MyPageInfoDto myPageInfoDto, Long memberSeq);

}
