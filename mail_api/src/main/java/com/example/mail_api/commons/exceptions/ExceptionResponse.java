package com.example.mail_api.commons.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// ExceptionResponse.java
@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private final HttpStatus httpStatus;
    private final String message;

    public ExceptionResponse(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }
}
