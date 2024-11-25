package com.example.mail_sender.domains.mail.services;

import com.example.mail_sender.domains.mail.dto.request.EmailRequest;
import com.example.mail_sender.domains.mail.dto.response.EmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
class EmailSendServ {
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${email.batch.size:10}")  // 기본값 10
    private int batchSize;

    @Async
    protected CompletableFuture<List<EmailResult>> sendEmails(List<EmailRequest> requests) {
        // 배치로 나누기
        List<List<EmailRequest>> batches = partitionList(requests, batchSize);

        List<CompletableFuture<List<EmailResult>>> futures = batches.stream()
                .map(this::sendBatch)
                .collect(Collectors.toList());

        // 모든 배치의 결과를 기다리고 합침
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));
    }

    // Lists.partition 대신 사용할 메서드
    private List<List<EmailRequest>> partitionList(List<EmailRequest> list, int size) {
        List<List<EmailRequest>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(new ArrayList<>(
                    list.subList(i, Math.min(i + size, list.size()))
            ));
        }
        return partitions;
    }

    private CompletableFuture<List<EmailResult>> sendBatch(List<EmailRequest> batch) {
        return CompletableFuture.supplyAsync(() -> {
            List<EmailResult> results = new ArrayList<>();

            try {
                // MimeMessagePreparator를 사용한 배치 처리
                MimeMessagePreparator[] preparators = batch.stream()
                        .map(this::createMimeMessagePreparator)
                        .toArray(MimeMessagePreparator[]::new);

                emailSender.send(preparators);

                // 성공 결과 생성
                results.addAll(batch.stream()
                        .map(req -> new EmailResult(req.getTo(), true, "Success"))
                        .collect(Collectors.toList()));

                log.info("Batch of {} emails sent successfully", batch.size());
            } catch (MailException e) {
                log.error("Failed to send batch - Error: {}", e.getMessage());
                // 실패 결과 생성
                results.addAll(batch.stream()
                        .map(req -> new EmailResult(req.getTo(), false, e.getMessage()))
                        .collect(Collectors.toList()));
            }

            return results;
        });
    }

    private MimeMessagePreparator createMimeMessagePreparator(EmailRequest request) {
        return mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom(fromEmail);
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getContent());
        };
    }
}