package com.example.mail_api.email.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Schema(description = "메일 발송 결과 DTO")
@Getter
@Builder
public class EmailSendResultResponseDTO {
    @Schema(description = "수신자 이메일", example = "user@example.com")
    private final String email;

    @Schema(
            description = "발송 시간",
            example = "2024-12-15T22:45:09.068275"
    )
    private final String timestamp;

    public static EmailSendResultResponseDTO of(String email) {
        return EmailSendResultResponseDTO.builder()
                .email(email)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}