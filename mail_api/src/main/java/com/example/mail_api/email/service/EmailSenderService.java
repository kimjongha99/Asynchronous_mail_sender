package com.example.mail_api.email.service;

import com.example.mail_api.commons.exceptions.CustomException;
import com.example.mail_api.commons.exceptions.ErrorCode;
import com.example.mail_api.email.dto.response.EmailSendResultResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


// EmailSenderService.java
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public EmailSendResultResponseDTO sendEmail(String to, String subject, String content, boolean isHtml) {
        try {
            log.info("Sending email to: {}", to);
            var mimeMessage = emailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);

            emailSender.send(mimeMessage);
            log.info("Email sent successfully to: {}", to);

            return EmailSendResultResponseDTO.of(to);
        } catch (MailException e) {
            log.error("Failed to send email to: {} - Error: {}", to, e.getMessage());
            throw new CustomException(ErrorCode.MAIL_SEND_FAILED);
        } catch (Exception e) {
            log.error("Unexpected error while sending email to: {} - Error: {}", to, e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
