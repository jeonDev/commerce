package com.commerce.core.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @Schema(description = "로그인 아이디", example = "test") @NotNull String id,
        @Schema(description = "패스워드", example = "1234") @NotNull String password
) {
}
