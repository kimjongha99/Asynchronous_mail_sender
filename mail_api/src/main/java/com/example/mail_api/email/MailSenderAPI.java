package com.example.mail_api.email;

import com.example.mail_api.commons.exceptions.CustomException;
import com.example.mail_api.commons.exceptions.ErrorCode;
import com.example.mail_api.commons.exceptions.ExceptionResponse;
import com.example.mail_api.email.dto.request.MailRequestDTO;
import com.example.mail_api.email.dto.response.EmailSendResultResponseDTO;
import com.example.mail_api.email.service.EmailServicesFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



// MailSenderAPI.java
@Tag(name = "Mail API", description = "메일 발송 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailSenderAPI {
    private final EmailServicesFacade emailServicesFacade;

    @Operation(
            summary = "메일 발송",
            description = """
            지정된 타입에 따라 이메일을 발송합니다.

            메일 타입별 필요한 templateParams:
            - PASSWORD_RESET: ["재설정링크"]
            - EMAIL_VERIFY: ["인증코드"]
            - WITHDRAWAL: ["사용자이름"]
            """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "메일 발송 성공",
                    content = @Content(
                            schema = @Schema(implementation = EmailSendResultResponseDTO.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                            "email": "user@example.com",
                            "timestamp": "2024-12-15T22:45:09.068275"
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                            "httpStatus": "BAD_REQUEST",
                            "message": "잘못된 이메일 형식입니다"
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                            "httpStatus": "INTERNAL_SERVER_ERROR",
                            "message": "메일 발송에 실패했습니다"
                        }
                        """
                            )
                    )
            )
    })
    @PostMapping("/send")
    public ResponseEntity<EmailSendResultResponseDTO> sendMail(
            @Valid @RequestBody MailRequestDTO mailRequestDTO
    ) {
        EmailSendResultResponseDTO result = emailServicesFacade.sendMail(mailRequestDTO);
        return ResponseEntity.ok(result);
    }
}
