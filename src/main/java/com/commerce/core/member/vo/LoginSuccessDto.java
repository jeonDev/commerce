package com.commerce.core.member.vo;

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

    private String accessToken;
    private String refreshToken;
}