package com.example.mail_api.commons.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

public class ResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error {
        private HttpStatus httpStatus;
        private String message;


    }
}