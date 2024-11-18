    package com.example.mail_sender;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.mail.MailException;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.mail.javamail.JavaMailSenderImpl;
    import org.springframework.stereotype.Service;
    import java.util.Properties;


    @Service
    @Slf4j
    public class EmailService {

        private final JavaMailSender emailSender;

        @Value("${spring.mail.username}")
        private String fromEmail;

        public EmailService(JavaMailSender emailSender) {
            this.emailSender = emailSender;
        }

        public void sendEmail(String to, String subject, String text) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);  // 반드시 인증된 계정의 이메일 주소여야 함
                message.setTo(to);
                message.setSubject(subject);
                message.setText(text);

                emailSender.send(message);
                log.info("Email sent successfully to: {}", to);
            } catch (MailException e) {
                log.error("Failed to send email to: {} - Error: {}", to, e.getMessage());
                throw new RuntimeException("Failed to send email", e);
            }
        }
    }

