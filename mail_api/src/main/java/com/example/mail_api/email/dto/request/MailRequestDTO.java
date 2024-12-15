package com.example.mail_api.email.dto.request;

import com.example.mail_api.email.service.enums.MailType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Schema(description = "메일 발송 요청 DTO")
@Getter
@AllArgsConstructor
public class MailRequestDTO {
    @Schema(
            description = "수신자 이메일",
            example = "user@example.com"
    )
    @Email(message = "올바른 이메일 형식이 아닙니다")
    @NotNull(message = "이메일은 필수입니다")
    private String email;

    @Schema(
            description = "메일 타입 (PASSWORD_RESET, EMAIL_VERIFY, WITHDRAWAL)",
            example = "EMAIL_VERIFY"
    )
    @NotNull(message = "메일 타입은 필수입니다")
    private MailType type;

    @Schema(
            description = """
            메일 템플릿에 필요한 파라미터 배열
            - PASSWORD_RESET: ["재설정링크"]
            - EMAIL_VERIFY: ["인증코드"]
            - WITHDRAWAL: ["사용자이름"]
            """,
            example = "[\"123456\"]"
    )
    private String[] templateParams;
}