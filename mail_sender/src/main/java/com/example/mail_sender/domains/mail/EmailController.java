package com.example.mail_sender.domains.mail;

import com.example.mail_sender.domains.mail.dto.request.EmailRequest;
import com.example.mail_sender.domains.mail.dto.response.EmailResult;
import com.example.mail_sender.domains.mail.services.EmailServicesFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailServicesFacade emailServicesFacade;

    @PostMapping("/bulk")
    public CompletableFuture<List<EmailResult>> sendBulkEmails(
            @RequestBody List<EmailRequest> requests) {
        return emailServicesFacade.sendBulkEmails(requests);
    }
}