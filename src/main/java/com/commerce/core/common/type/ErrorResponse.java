package com.commerce.core.common.type;

import java.sql.Timestamp;

public record ErrorResponse(
        String code,
        String message,
        Timestamp timestamp
) {

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, new Timestamp(System.currentTimeMillis()));
    }
}
