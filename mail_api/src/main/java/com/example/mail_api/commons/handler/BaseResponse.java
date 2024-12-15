package com.example.mail_api.commons.handler;

import com.example.mail_api.commons.exceptions.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;

    private BaseResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // 성공 응답 (데이터 있음)
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "요청 성공", data);
    }

    // 성공 응답 (데이터 없음)
    public static BaseResponse<Void> success() {
        return new BaseResponse<>(true, "요청 성공", null);
    }

    // 성공 응답 (메시지 커스텀)
    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(true, message, data);
    }

    // 실패 응답
    public static <T> BaseResponse<T> failure(String message) {
        return new BaseResponse<>(false, message, null);
    }
}