package com.example.apigateway.service;

import com.example.apigateway.dto.CredentialDto;
import reactor.core.publisher.Mono;

public interface AuthenticationClient {
    Mono<String> registerCredentials(CredentialDto credentials);
    Mono<Void> rollbackRegistration(String userId);
}
