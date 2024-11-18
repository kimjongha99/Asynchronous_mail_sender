package com.example.mail_sender.domains.mail.services;


import com.example.mail_sender.domains.mail.dto.request.EmailRequest;
import com.example.mail_sender.domains.mail.dto.response.EmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServicesFacade {
    private final EmailSendServ emailSendServ;
    private final EmailValidateServ emailValidateServ;
    private final EmailResultServ emailResultServ;

    @Transactional
    public CompletableFuture<List<EmailResult>> sendBulkEmails(List<EmailRequest> requests) {
        // 이메일 유효성 검증
        emailValidateServ.validateEmailRequests(requests);

        // 비동기 이메일 발송
        List<EmailResult> results = emailSendServ.sendEmails(requests);

        // 결과 처리 및 반환
        return emailResultServ.processResults(results);
    }
}