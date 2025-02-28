package com.commerce.core.api.response;

import lombok.Builder;

@Builder
public record MyInfoResponse(
        String id,
        String name,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
        Long point
) {
}
