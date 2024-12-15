package com.example.mail_api.email.dto.request;

import com.example.mail_api.email.service.enums.MailType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MailRequestDTO {
    @Email
    @NotNull
    private String email;

    @NotNull
    private MailType type;
}