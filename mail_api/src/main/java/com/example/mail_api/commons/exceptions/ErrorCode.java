package com.example.mail_api.commons.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "메일 발송에 실패했습니다"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다"),
    INVALID_MAIL_TEMPLATE(HttpStatus.BAD_REQUEST, "잘못된 메일 템플릿 타입입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST, "서버오류" ),
    INVALID_MAIL_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 메일 타입입니다"),
    MAIL_TEMPLATE_PARAMS_REQUIRED(HttpStatus.BAD_REQUEST, "메일 템플릿 파라미터가 필요합니다");



    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}/*
HTTP 상태 코드 가이드:

1xx (정보)
- 100 Continue: 진행 중
- 101 Switching Protocols: 프로토콜 전환

2xx (성공)
- 200 OK: 요청 성공
- 201 Created: 리소스 생성 성공
- 204 No Content: 성공했지만 응답 본문 없음

3xx (리다이렉션)
- 301 Moved Permanently: 영구 이동
- 302 Found: 임시 이동
- 304 Not Modified: 캐시된 리소스 사용

4xx (클라이언트 오류)
- 400 Bad Request: 잘못된 요청 구문
- 401 Unauthorized: 인증 필요
- 403 Forbidden: 권한 없음
- 404 Not Found: 리소스를 찾을 수 없음
- 409 Conflict: 리소스 충돌
- 429 Too Many Requests: 요청 횟수 초과

5xx (서버 오류)
- 500 Internal Server Error: 서버 내부 오류
- 502 Bad Gateway: 게이트웨이 오류
- 503 Service Unavailable: 서비스 이용 불가
- 504 Gateway Timeout: 게이트웨이 시간 초과
*/
