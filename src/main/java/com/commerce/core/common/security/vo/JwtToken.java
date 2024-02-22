package com.commerce.core.common.security.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtToken {
    ACCESS_TOKEN(100L), REFRESH_TOKEN(60*60*1L);

    private final Long expiredTime;
}
