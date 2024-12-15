package com.example.mail_api.email.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailType {
    PASSWORD_RESET("비밀번호 재설정"),
    EMAIL_VERIFY("이메일 인증"),
    WITHDRAWAL("회원탈퇴");

    private final String description;
}