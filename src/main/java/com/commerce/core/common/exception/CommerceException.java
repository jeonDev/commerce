package com.commerce.core.common.exception;

import lombok.Getter;

@Getter
public class CommerceException extends RuntimeException {

    private final String code;

    public CommerceException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.code = exceptionStatus.getCode();
    }
}
