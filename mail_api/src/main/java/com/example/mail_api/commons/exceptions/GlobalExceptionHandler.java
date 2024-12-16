package com.example.mail_api.commons.exceptions;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 기존 CustomException 핸들러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    // Enum 변환 에러 등 JSON 파싱 에러 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        ErrorCode errorCode = ErrorCode.INVALID_MAIL_TYPE;  // 새로 추가한 에러 코드 사용

        // Enum 변환 실패 에러 메시지를 로그로 남김
        log.error("Invalid request format: {}", e.getMessage());

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ExceptionResponse(errorCode));
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Unexpected error occurred", e);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ExceptionResponse(errorCode));
    }

    // Validation 예외 처리 추가
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(
            MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("잘못된 요청입니다");

        log.error("Validation error: {}", message);

        ErrorCode errorCode = ErrorCode.INVALID_EMAIL_FORMAT;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ExceptionResponse(errorCode));
    }


}

