package com.wanted.sns_feed_service.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title = "sns",
                description = "sns api - personal project",
                version = "v1")
)
@Configuration
public class SwaggerConfig {
    // @Bean
    // public GroupedOpenApi snsOpenApi(){
    //     String[] paths = {"/v1/**"};
    //
    //     return GroupedOpenApi.builder()
    //             .group("sns")
    //             .pathsToMatch(paths)
    //             .build();
    // }
}
