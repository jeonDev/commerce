package com.commerce.core.api.response;

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
}
