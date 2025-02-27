package com.commerce.core.member.vo;

import com.commerce.core.api.response.LoginResponse;
import com.commerce.core.common.config.security.vo.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginSuccessDto {
    private String id;
    private String name;
    private String tel;
    private String addr;
    private String addrDetail;
    private String zipCode;
    private Authority authority;

    private String accessToken;
    private String refreshToken;

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