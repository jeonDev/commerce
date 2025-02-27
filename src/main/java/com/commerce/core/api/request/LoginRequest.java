package com.commerce.core.api.request;

import com.commerce.core.member.vo.LoginDto;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull String id,
        @NotNull String password
) {
    public LoginDto requestToDto() {
        return LoginDto.builder()
                .id(id)
                .password(password)
                .build();
    }
}
