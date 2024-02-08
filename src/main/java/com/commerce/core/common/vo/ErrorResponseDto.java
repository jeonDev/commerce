package com.commerce.core.common.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponseDto {
    private String code;
    private String message;
    private Timestamp timestamp;

    @Builder
    public ErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
