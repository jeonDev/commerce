package com.commerce.core.member.service.request;

import com.commerce.core.common.config.security.vo.Authority;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.vo.oauth.OAuthType;
import lombok.Builder;

@Builder
public record MemberServiceRequest(
        Long memberSeq,
        String id,
        String name,
        String password,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
        OAuthType oAuthType
) {



    public Member toEntity() {
        return Member.builder()
                .id(id)
                .memberSeq(memberSeq)
                .name(name)
                .password(password)
                .tel(tel)
                .addr(addr)
                .addrDetail(addrDetail)
                .zipCode(zipCode)
                .passwordFailCount(0L)
                .authority(Authority.ROLE_USER)
                .useYn("Y")
                .oauthType(oAuthType)
                .build();
    }
}
