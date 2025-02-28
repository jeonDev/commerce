package com.commerce.core.member.service.request;

import lombok.Builder;

@Builder
public record MemberUpdateServiceRequest(
        String id,
        String name,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
        Long point
) {
}
