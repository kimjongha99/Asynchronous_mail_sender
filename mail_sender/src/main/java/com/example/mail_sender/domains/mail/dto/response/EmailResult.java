package com.example.mail_sender.domains.mail.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailResult {
    private String email;
    private boolean success;
    private String message;
}