package com.taxi.backend.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi UserApi() {
        return GroupedOpenApi.builder()
                .group("spring-public")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("spring-driver")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public GroupedOpenApi DriverApi() {
        return GroupedOpenApi.builder()
                .group("spring-admin")
                .pathsToMatch("/**")
                .build();
    }
}