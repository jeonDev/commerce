package com.commerce.core.common.config.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtIdentificationGenerateVO implements IdentificationGenerateVO {

    private String id;
    private Authority authority;
    private JwtToken jwtToken;
}
