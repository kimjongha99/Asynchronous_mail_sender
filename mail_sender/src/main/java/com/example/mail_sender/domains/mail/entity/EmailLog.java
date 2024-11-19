package com.example.mail_sender.domains.mail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_logs")
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus status;

    @Column
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public enum EmailStatus {
        SUCCESS, FAILURE
    }

    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }

    // 실패 로그 생성을 위한 정적 팩토리 메서드
    public static EmailLog createFailureLog(String recipient, String errorMessage) {
        return EmailLog.builder()
                .recipient(recipient)
                .status(EmailStatus.FAILURE)
                .errorMessage(errorMessage)
                .build();
    }

    // 성공 로그 생성을 위한 정적 팩토리 메서드
    public static EmailLog createSuccessLog(String recipient) {
        return EmailLog.builder()
                .recipient(recipient)
                .status(EmailStatus.SUCCESS)
                .build();
    }
}