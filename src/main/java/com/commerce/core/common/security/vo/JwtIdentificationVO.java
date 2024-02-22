package com.commerce.core.common.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtIdentificationVO implements IdentificationVO {

    private String name;
    private JwtToken jwtToken;
}
