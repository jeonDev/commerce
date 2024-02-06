package com.commerce.core.common.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponseDto {
    private String code;
    private String message;
    private LocalDateTime timestamp;

    @Builder
    public ErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
