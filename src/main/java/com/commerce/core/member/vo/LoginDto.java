package com.commerce.core.member.vo;

import lombok.Builder;

@Builder
public record LoginDto(
        String id,
        String password
) {
}
