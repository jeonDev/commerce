package com.commerce.core.member.service.response;

import com.commerce.core.api.response.LoginResponse;
import com.commerce.core.common.config.security.vo.Authority;
import lombok.Builder;

@Builder
public record LoginServiceResponse(
        String id,
        String name,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
        Authority authority,
        String accessToken,
        String refreshToken
) {

    public LoginResponse toLoginResponse() {
        return LoginResponse.builder()
                .id(id)
                .name(name)
                .tel(tel)
                .addr(addr)
                .addrDetail(addrDetail)
                .zipCode(zipCode)
                .authority(authority.name())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}