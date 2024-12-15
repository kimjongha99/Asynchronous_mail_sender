package com.example.mail_api.commons.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 클라이언트 오류 (400번대)
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 매개 변수"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    DUPLICATE_EXTENSION(HttpStatus.CONFLICT, "확장명 중복"),
    INVALID_VALIDATION(HttpStatus.BAD_REQUEST, "유효성 검사 실패" ),
    CUSTOM_EXTENSION_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "커스텀 확장자 개수 한도를 초과"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,"이메일이 중복입니다." ),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호 실패입니다." ),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."),

    InvalidSessionSchedule(HttpStatus.BAD_REQUEST, "종료 시간이 시작 시간보다 빠를 수 없습니다." ),

    DuplicateParticipant(HttpStatus.BAD_REQUEST, "참가자 중복." ),

    AlreadyParticipant(HttpStatus.BAD_REQUEST, "이미 들어온 유저." ) ,

    SessionParticipantsFull(HttpStatus.BAD_REQUEST, "이미 풀방이에요"),

    SessionNotFound(HttpStatus.BAD_REQUEST, "세션이 없다"),
    SettingsNotFound(HttpStatus.BAD_REQUEST, "세팅이 없다" ),
    // JWT 관련 오류
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "잘못된 토큰 형식입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 서명입니다."),



    // 서버 오류 (500번대)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류"),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "인증 실패"),

    // 클라이언트 오류 (400번대)
    InvalidRoomAccess(HttpStatus.BAD_REQUEST, "유효하지 않은 방 접근"),
    UserNotFound(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다"),

    UserNotInRoom(HttpStatus.BAD_REQUEST, "사용자가 방에 없음"), ParticipantNotFound(HttpStatus.BAD_REQUEST, "ParticipantNotFound"),
    SESSION_NOT_FOUND(HttpStatus.BAD_REQUEST, "SESSION_NOT_FOUND" ),
    SESSION_NOT_IN_PROGRESS(HttpStatus.BAD_REQUEST, "SESSION_NOT_IN_PROGRESS" ),
    USER_NOT_INVITED(HttpStatus.BAD_REQUEST, "USER_NOT_INVITED" ),
    SESSION_SETTINGS_NOT_FOUND(HttpStatus.BAD_REQUEST, "SESSION_SETTINGS_NOT_FOUND" ),
    InvalidSessionStatus(HttpStatus.BAD_REQUEST, "SESSION_SETTINGS_NOT_FOUND" ),
    UserNotInvited(HttpStatus.BAD_REQUEST, "UserNotInvited" ),




    MESSAGE_SEND_FAILED(HttpStatus.BAD_REQUEST, "MESSAGE_SEND_FAILED"),


    INVALID_ROOM_ID(HttpStatus.BAD_REQUEST, "INVALID_ROOM_ID"),

    USER_INVALID_STATUS(HttpStatus.BAD_REQUEST, "INVALID_ROOM_ID" ),

    USER_ALREADY_PARTICIPANT(HttpStatus.BAD_REQUEST, "USER_ALREADY_PARTICIPANT");



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
