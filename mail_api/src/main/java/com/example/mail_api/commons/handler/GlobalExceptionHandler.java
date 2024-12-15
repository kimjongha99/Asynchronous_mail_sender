package com.example.mail_api.commons.handler;

import com.example.mail_api.commons.exceptions.CustomException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public BaseResponse<Void> handleCustomException(CustomException e) {
        log.error("CustomException occurred: {}", e.getMessage());
        return BaseResponse.failure(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception e) {
        log.error("Unexpected error occurred", e);
        return BaseResponse.failure("서버 내부 오류가 발생했습니다");
    }
}