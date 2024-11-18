package com.example.mail_sender;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String content;
}