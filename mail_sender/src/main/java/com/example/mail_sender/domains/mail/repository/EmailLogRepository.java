package com.example.mail_sender.domains.mail.repository;

import com.example.mail_sender.domains.mail.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
    // 필요한 쿼리 메서드를 여기에 추가할 수 있습니다
}