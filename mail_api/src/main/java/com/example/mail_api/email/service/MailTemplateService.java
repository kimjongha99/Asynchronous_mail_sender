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
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f9f9f9; border-radius: 5px; }
                        h2 { color: #2c3e50; border-bottom: 2px solid #2A2F6E; padding-bottom: 10px; }
                        .btn { display: inline-block; padding: 10px 20px; background-color: #2A2F6E; color: #ffffff; text-decoration: none; border-radius: 5px; }
                        .footer { margin-top: 20px; font-size: 0.9em; color: #7f8c8d; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>비밀번호 재설정</h2>
                        <p>안녕하세요. 비밀번호 재설정을 요청하셨습니다.</p>
                        <p>아래 버튼을 클릭하여 비밀번호를 재설정하세요:</p>
                        <p><a href="%s" class="btn">비밀번호 재설정</a></p>
                        <p class="footer">이 링크는 30분 동안 유효합니다. 요청하지 않으셨다면 이 이메일을 무시하세요.</p>
                    </div>
                    
             <!-- FOOTER -->
                 <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                  <tr>
                      <td bgcolor="#2A2F6E" align="center" style="padding: 30px 30px 30px 30px; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;">
                          <p style="margin: 0;">&copy; 2024 디엠티랩스. All rights reserved.</p>
                          <p style="margin: 10px 0 0 0;">주소: 서울특별시 용산구 한강대로 40길 18 | 이메일: ksaas-noreply@dmtlabs.co.kr</p>
                      </td>
                  </tr>
                </table>
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
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f9f9f9; border-radius: 5px; }
                        h2 { color: #2c3e50; border-bottom: 2px solid #2A2F6E; padding-bottom: 10px; }
                        .code { font-size: 24px; font-weight: bold; color: #2A2F6E; letter-spacing: 5px; }
                        .footer { margin-top: 20px; font-size: 0.9em; color: #7f8c8d; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>이메일 인증</h2>
                        <p>안녕하세요. 귀하의 이메일 인증 코드입니다:</p>
                        <p class="code">%s</p>
                        <p>위 코드를 인증 화면에 입력해 주세요.</p>
                        <p class="footer">이 코드는 10분 동안 유효합니다. 요청하지 않으셨다면 이 이메일을 무시하세요.</p>
                    </div>
                     <!-- FOOTER -->
                         <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                          <tr>
                              <td bgcolor="#2A2F6E" align="center" style="padding: 30px 30px 30px 30px; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;">
                                  <p style="margin: 0;">&copy; 2024 디엠티랩스. All rights reserved.</p>
                                  <p style="margin: 10px 0 0 0;">주소: 서울특별시 용산구 한강대로 40길 18 | 이메일: ksaas-noreply@dmtlabs.co.kr</p>
                              </td>
                          </tr>
                        </table>
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
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f9f9f9; border-radius: 5px; }
                        h2 { color: #2c3e50; border-bottom: 2px solid #2A2F6E; padding-bottom: 10px; }
                        .username { font-weight: bold; color: #2A2F6E; }
                        .footer { margin-top: 20px; font-size: 0.9em; color: #7f8c8d; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>회원 탈퇴 완료</h2>
                        <p><span class="username">%s</span>님의 회원 탈퇴가 완료되었습니다.</p>
                        <p>그동안 저희 서비스를 이용해 주셔서 진심으로 감사드립니다.</p>
                        <p>향후 더 나은 서비스로 다시 만나뵙기를 희망합니다.</p>
                        <p class="footer">추가 문의사항이 있으시면 언제든 고객센터로 연락 주시기 바랍니다.</p>
                    </div>
                     <!-- FOOTER -->
                         <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                          <tr>
                              <td bgcolor="#2A2F6E" align="center" style="padding: 30px 30px 30px 30px; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;">
                                  <p style="margin: 0;">&copy; 2024 디엠티랩스. All rights reserved.</p>
                                  <p style="margin: 10px 0 0 0;">주소: 서울특별시 용산구 한강대로 40길 18 | 이메일: ksaas-noreply@dmtlabs.co.kr</p>
                              </td>
                          </tr>
                        </table>
                </body>
                </html>
            """.formatted(username);
    }
}