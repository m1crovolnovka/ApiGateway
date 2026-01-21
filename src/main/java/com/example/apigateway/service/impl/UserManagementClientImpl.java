package com.example.apigateway.service.impl;

import com.example.apigateway.dto.UserProfileDto;
import com.example.apigateway.service.UserManagementClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserManagementClientImpl implements UserManagementClient {

    private final WebClient webClient;

    @Value("${service.user.uri.create}")
    private String createUri;

    public UserManagementClientImpl(@Qualifier("loadBalancedWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> saveUserProfile(UserProfileDto profile) {
        return webClient.post()
                .uri(createUri)
                .bodyValue(profile)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
