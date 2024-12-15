package com.example.mail_api.commons.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi mailGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("mail-sender")
                .pathsToMatch("/api/mail/**")
                .addOpenApiCustomizer(openApi ->
                        openApi.setInfo(new Info()
                                .title("Mail Sender API")
                                .description("메일 발송 서비스를 위한 API")
                                .version("1.0.0")
                        )
                )
                .build();
    }
}