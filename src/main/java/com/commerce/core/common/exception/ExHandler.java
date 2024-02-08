package com.commerce.core.common.exception;

import com.commerce.core.common.vo.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExHandler {

    @ExceptionHandler(CommerceException.class)
    protected ResponseEntity<ErrorResponseDto> handlerCommerceException(CommerceException e) {
        ErrorResponseDto errorDto = ErrorResponseDto.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorDto, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handlerException(Exception e) {
        ErrorResponseDto errorDto = ErrorResponseDto.builder()
                .code("9999")
                .message("System Error")
                .build();

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
