package com.commerce.core.common.config.security.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtToken {
    ACCESS_TOKEN(60 * 60 * 60 * 30L), REFRESH_TOKEN(60 * 60 * 60 * 10L);

    private final Long expiredTime;
}
