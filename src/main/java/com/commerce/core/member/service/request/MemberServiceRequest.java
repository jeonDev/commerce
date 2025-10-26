package com.commerce.core.member.service.request;

import com.commerce.core.common.config.security.type.Authority;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.type.oauth.OAuthType;
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
        return Member.of(
                id,
                password,
                name,
                tel,
                addr,
                addrDetail,
                zipCode,
                Authority.ROLE_USER,
                oAuthType
                );
    }
}
