package com.commerce.core.member.service.request;

import lombok.Builder;

@Builder
public record LoginServiceRequest(
        String id,
        String password
) {
}
