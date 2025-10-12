package com.commerce.core.api.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull String id,
        @NotNull String password
) {
}
