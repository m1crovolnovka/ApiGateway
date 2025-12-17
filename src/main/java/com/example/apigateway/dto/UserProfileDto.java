package com.example.apigateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record UserProfileDto(
    UUID userId,

    @NotBlank(message = "Name cannot be blank")
    String name,

    @NotBlank(message = "Surname cannot be blank")
    String surname,

    @Past(message = "Birth date must be in the past")
    LocalDate birthDate,

    @NotBlank
    @Email(message = "Email must be valid")
    String email
) {
    public UserProfileDto withUserId(String id) {
        return new UserProfileDto(UUID.fromString(id),this.name, this.surname,this.birthDate, this.email());
    }
}
