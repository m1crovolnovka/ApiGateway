package com.example.apigateway.service;

import com.example.apigateway.dto.UserProfileDto;
import reactor.core.publisher.Mono;

public interface UserManagementClient {
    Mono<Void> saveUserProfile(UserProfileDto profile);
}
