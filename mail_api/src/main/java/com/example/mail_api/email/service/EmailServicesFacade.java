package com.example.mail_api.email.service;
import com.example.mail_api.email.dto.request.MailRequestDTO;
import com.example.mail_api.email.dto.response.EmailSendResultResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;




// EmailServicesFacade.java
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServicesFacade {
    private final EmailSenderService emailSenderService;
    private final MailTemplateService mailTemplateService;

    public EmailSendResultResponseDTO sendMail(MailRequestDTO request) {
        log.info("유형에 대한 메일 요청 처리 중: {}", request.getType());

        String subject = request.getType().getDescription();
        String content = mailTemplateService.getMailTemplate(
                request.getType(),
                request.getTemplateParams()
        );

        return emailSenderService.sendEmail(
                request.getEmail(),
                subject,
                content,
                true
        );
    }
}