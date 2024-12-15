package com.example.mail_api.email.service.enums;

import com.example.mail_api.commons.exceptions.CustomException;
import com.example.mail_api.commons.exceptions.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailType {
    PASSWORD_RESET("비밀번호 재설정"),
    EMAIL_VERIFY("이메일 인증"),
    WITHDRAWAL("회원탈퇴");

    private final String description;

    public void validateTemplateParams(String[] params) {
        if (params == null || params.length == 0) {
            throw new CustomException(ErrorCode.MAIL_TEMPLATE_PARAMS_REQUIRED);
        }
    }
}