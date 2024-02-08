package com.commerce.core.common.vo;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResponseVO<T> {
    private String code;
    private String message;
    private T data;
    private Timestamp timestamp;

    @Builder
    public ResponseVO(String code, String message, T data) {
        this.code = code == null ? "0000" : code;
        this.message = message == null ? "Success" : message;
        this.data = data;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
