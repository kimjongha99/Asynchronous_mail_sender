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
class EmailResultServ {
    protected CompletableFuture<List<EmailResult>> processResults(List<EmailResult> results) {
        logResults(results);
        return CompletableFuture.completedFuture(results);
    }

    private void logResults(List<EmailResult> results) {
        long successCount = results.stream().filter(EmailResult::isSuccess).count();
        long failureCount = results.size() - successCount;

        log.info("Email sending completed. Success: {}, Failure: {}", successCount, failureCount);

        results.stream()
                .filter(result -> !result.isSuccess())
                .forEach(result -> log.error("Failed email: {} - Reason: {}",
                        result.getEmail(), result.getMessage()));
    }
}
