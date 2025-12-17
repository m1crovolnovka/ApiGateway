package com.example.apigateway.service.impl;

import com.example.apigateway.dto.CredentialDto;
import com.example.apigateway.service.AuthenticationClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationClientImpl implements AuthenticationClient {
    @Value("${service.auth.uri.register}")
    private String registerUri;

    @Value("${service.auth.uri.rollback}")
    private String rollbackUri;

    private final WebClient webClient;

    public AuthenticationClientImpl(@Qualifier("loadBalancedWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<String> registerCredentials(CredentialDto credentials) {
        return webClient.post()
                .uri(registerUri)
                .bodyValue(credentials)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<Void> rollbackRegistration(String userId) {
        return webClient.delete()
                .uri(rollbackUri, userId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
