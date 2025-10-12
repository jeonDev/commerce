package com.commerce.core.member.facade;

import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.service.request.MemberUpdateServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberFacade {
    private final MemberService memberService;

    public MemberFacade(MemberService memberService) {
        this.memberService = memberService;
    }

    public void create(MemberServiceRequest request) {
        memberService.createMember(request);
    }

    public void update(MemberUpdateServiceRequest request, Long memberSeq) {
        memberService.updateMember(request, memberSeq);
    }
}
