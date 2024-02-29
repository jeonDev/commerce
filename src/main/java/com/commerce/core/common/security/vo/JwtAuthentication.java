package com.commerce.core.common.security.vo;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthentication implements AuthenticationInfo {
    private final String id;
    private final Authority authority;
    private final String name;
}
