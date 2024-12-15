package com.example.mail_api.email.service;


import com.example.mail_api.email.service.enums.MailType;
import org.springframework.stereotype.Service;

@Service
public class MailTemplateService {

    public String getMailTemplate(MailType type, String... params) {
        type.validateTemplateParams(params);

        return switch (type) {
            case PASSWORD_RESET -> generatePasswordResetTemplate(params[0]); // token
            case EMAIL_VERIFY -> generateVerificationTemplate(params[0]);    // code
            case WITHDRAWAL -> generateWithdrawalTemplate(params[0]);        // username
        };
    }

    private String generatePasswordResetTemplate(String resetToken) {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <title>비밀번호 재설정</title>
            </head>
            <body>Ω
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h2>비밀번호 재설정</h2>
                    <p>아래 링크를 클릭하여 비밀번호를 재설정하세요:</p>
                    <a href="%s">비밀번호 재설정</a>
                    <p>이 링크는 30분 동안 유효합니다.</p>
                </div>
            </body>
            </html>
            """.formatted(resetToken);
    }

    private String generateVerificationTemplate(String verificationCode) {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <title>이메일 인증</title>
            </head>
            <body>
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h2>이메일 인증</h2>
                    <p>인증 코드: <strong>%s</strong></p>
                    <p>이 코드는 10분 동안 유효합니다.</p>
                </div>
            </body>
            </html>
            """.formatted(verificationCode);
    }

    private String generateWithdrawalTemplate(String username) {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <title>회원 탈퇴 완료</title>
            </head>
            <body>
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h2>회원 탈퇴 완료</h2>
                    <p>%s님의 회원 탈퇴가 완료되었습니다.</p>
                    <p>그동안 서비스를 이용해 주셔서 감사합니다.</p>
                </div>
            </body>
            </html>
            """.formatted(username);
    }
}