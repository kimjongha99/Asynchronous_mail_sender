package com.example.mail_sender.domains.mail.services;

import com.example.mail_sender.domains.mail.dto.request.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class EmailValidateServ {
    protected void validateEmailRequests(List<EmailRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Email requests cannot be empty");
        }

        requests.forEach(this::validateSingleRequest);
    }

    private void validateSingleRequest(EmailRequest request) {
        if (request.getTo() == null || request.getTo().isEmpty()) {
            throw new IllegalArgumentException("Email recipient cannot be empty");
        }
        if (!isValidEmail(request.getTo())) {
            throw new IllegalArgumentException("Invalid email address: " + request.getTo());
        }
        if (request.getSubject() == null || request.getSubject().isEmpty()) {
            throw new IllegalArgumentException("Email subject cannot be empty");
        }
        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new IllegalArgumentException("Email content cannot be empty");
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
