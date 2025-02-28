package com.commerce.core.member.service;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.service.request.MemberUpdateServiceRequest;
import com.commerce.core.member.service.response.MyPageInfoServiceResponse;

import java.util.Optional;

public interface MemberService {

    Member createMember(MemberServiceRequest request);
    Optional<Member> selectMember(Long memberSeq);
    Optional<Member> selectUseMember(Long memberSeq);
    Optional<Member> selectUseMember(String id);
    Member save(Member member);
    MyPageInfoServiceResponse selectMyInfo(Long memberSeq);
    void updateMember(MemberUpdateServiceRequest request, Long memberSeq);

}
