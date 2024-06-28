package com.commerce.core.common.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtIdentificationGenerateVO implements IdentificationGenerateVO {

    private String name;
    private Authority authority;
    private JwtToken jwtToken;
}
