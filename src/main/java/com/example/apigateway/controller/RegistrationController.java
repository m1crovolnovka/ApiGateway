package com.example.apigateway.controller;

import com.example.apigateway.dto.RegistrationRequest;
import com.example.apigateway.dto.UserProfileDto;
import com.example.apigateway.service.AuthenticationClient;
import com.example.apigateway.service.UserManagementClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final AuthenticationClient authClient;
    private final UserManagementClient userClient;

    public RegistrationController(AuthenticationClient authClient, UserManagementClient userClient) {
        this.authClient = authClient;
        this.userClient = userClient;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registerUser(@RequestBody RegistrationRequest request) {
        return authClient.registerCredentials(request.credentials())
                .flatMap(userId -> {
                    UserProfileDto profile = request.profile().withUserId(userId);
                    return userClient.saveUserProfile(profile)
                            .onErrorResume(e -> {
                                return authClient.rollbackRegistration(userId)
                                        .then(Mono.error(new RuntimeException("Registration failed, rollback complete: " + e.getMessage(), e)));
                            });
                })
                .then();
    }
}
