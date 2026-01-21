package com.example.apigateway.dto;

public record RegistrationRequest(
        CredentialDto credentials,
        UserProfileDto profile
) {}
