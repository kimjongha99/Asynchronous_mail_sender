package com.example.mail_api.email;

import com.example.mail_api.commons.handler.BaseResponse;
import com.example.mail_api.email.dto.request.MailRequestDTO;
import com.example.mail_api.email.service.EmailServicesFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "Mail API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailSenderAPI {

    private final EmailServicesFacade emailServicesFacade;

    @Operation(summary = "메일 발송")
    @PostMapping("/send")
    public BaseResponse<Void> sendMail(@RequestBody MailRequestDTO mailRequestDTO) {
        emailServicesFacade.sendMail(request);
        return BaseResponse.success();
    }
}