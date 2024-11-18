package com.example.mail_sender.domains.mail.services;

import com.example.mail_sender.domains.mail.dto.request.EmailRequest;
import com.example.mail_sender.domains.mail.dto.response.EmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class EmailSendServ {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;  // FROM 이메일 주소

    @Async
    protected List<EmailResult> sendEmails(List<EmailRequest> requests) {
        return requests.stream()
                .map(this::sendSingleEmail)
                .collect(Collectors.toList());
    }

    private EmailResult sendSingleEmail(EmailRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);  // 설정된 FROM 이메일 사용
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getContent());

            emailSender.send(message);
            log.info("Email sent successfully to: {}", request.getTo());
            return new EmailResult(request.getTo(), true, "Success");

        } catch (Exception e) {
            log.error("Failed to send email to: {} - Error: {}", request.getTo(), e.getMessage());
            return new EmailResult(request.getTo(), false, e.getMessage());
        }
    }
}