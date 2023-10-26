package com.wanted.sns_feed_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "sns",
                description = "sns api - personal project",
                version = "v1")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi snsOpenApi(){
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("sns")
                .pathsToMatch(paths)
                .build();
    }
}
