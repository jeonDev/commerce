package com.commerce.core.api.response;

import com.commerce.core.member.service.response.LoginServiceResponse;
import lombok.Builder;

@Builder
public record LoginResponse(
        String id,
        String name,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
        String authority,
        String accessToken,
        String refreshToken
) {
    public static LoginResponse from(LoginServiceResponse response) {
        return LoginResponse.builder()
                .id(response.id())
                .name(response.name())
                .tel(response.tel())
                .addr(response.addr())
                .addrDetail(response.addrDetail())
                .zipCode(response.zipCode())
                .authority(response.authority().name())
                .accessToken(response.accessToken())
                .refreshToken(response.refreshToken())
                .build();
    }
}
