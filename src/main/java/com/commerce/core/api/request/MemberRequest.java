package com.commerce.core.api.request;

import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.vo.oauth.OAuthType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record MemberRequest(
        @NotNull String id,
        @NotNull String name,
        @NotNull String password,
        @NotNull String tel,
        @NotNull String addr,
        @NotNull String addrDetail,
        @NotNull String zipCode,
        @Null OAuthType oAuthType
) {

    public MemberServiceRequest toRequest() {
        return MemberServiceRequest.builder()
                .id(id)
                .name(name)
                .password(password)
                .tel(tel)
                .addr(addr)
                .addrDetail(addrDetail)
                .zipCode(zipCode)
                .oAuthType(oAuthType)
                .build();
    }
}
