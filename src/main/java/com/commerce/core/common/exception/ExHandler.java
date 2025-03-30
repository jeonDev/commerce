package com.commerce.core.common.exception;

import com.commerce.core.common.type.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExHandler {

    @ExceptionHandler(CommerceException.class)
    protected ResponseEntity<ErrorResponse> handlerCommerceException(CommerceException e) {
        log.error("[Error] handlerCommerceException : [{}] {}", e.getCode(), e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.of(e.getCode(), e.getMessage());
        if (ExceptionStatus.AUTH_UNAUTHORIZED.getCode().equals(e.getCode()) )
            return new ResponseEntity<>(errorResponse,
                    HttpStatus.UNAUTHORIZED);
        if (ExceptionStatus.AUTH_FORBIDDEN.getCode().equals(e.getCode()) )
            return new ResponseEntity<>(errorResponse,
                    HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handlerMethodArgNotValidException(MethodArgumentNotValidException e) {
        log.error("[Error] handlerMethodArgNotValidException : {}", e.getMessage());
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(ErrorResponse.of(ExceptionStatus.VALID_ERROR.getCode(), message),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handlerException(Exception e) {
        log.error("[Error] handlerException : {}", e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.of(ExceptionStatus.SYSTEM_ERROR.getCode(), ExceptionStatus.SYSTEM_ERROR.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
