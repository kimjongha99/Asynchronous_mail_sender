package com.example.mail_sender.domains.mail.dto.request;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String content;
}