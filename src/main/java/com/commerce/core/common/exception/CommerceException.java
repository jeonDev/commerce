package com.commerce.core.common.exception;

import lombok.Getter;

@Getter
public class CommerceException extends RuntimeException {

    private final String code;
    private final String message;

    public CommerceException(ExceptionStatus exceptionStatus) {
        this.code = exceptionStatus.getCode();
        this.message = exceptionStatus.getMessage();
    }

    public CommerceException(Exception e) {
        super(e);
        this.code = "error";
        this.message = e.getMessage();
    }
}
