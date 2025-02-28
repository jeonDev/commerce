package com.commerce.core.api.request;

import com.commerce.core.member.service.request.LoginServiceRequest;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull String id,
        @NotNull String password
) {
    public LoginServiceRequest toRequest() {
        return LoginServiceRequest.builder()
                .id(id)
                .password(password)
                .build();
    }
}
