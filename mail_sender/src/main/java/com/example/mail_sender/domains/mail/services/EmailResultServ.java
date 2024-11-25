package com.example.mail_sender.domains.mail.services;

import com.example.mail_sender.domains.mail.dto.response.EmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailResultServ {
    // List<EmailResult>를 받는 대신 CompletableFuture<List<EmailResult>>를 받도록 수정
    public CompletableFuture<List<EmailResult>> processResults(CompletableFuture<List<EmailResult>> results) {
        return results.thenApply(emailResults -> {
            // 결과 처리 로직
            return emailResults;
        });
    }
}
