package com.commerce.core.common.type;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HttpResponse<T> {
    private String code;
    private String message;
    private T data;
    private Timestamp timestamp;

    @Builder
    public HttpResponse(String code, String message, T data) {
        this.code = code == null ? "0000" : code;
        this.message = message == null ? "Success" : message;
        this.data = data;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
