package com.example.contractservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfig {

    @Bean("camunda")
    public WebClient getWebClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://172.16.15.72:8082/api/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Bearer token123")
                .build();
        return webClient;
    }
}
