package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record User(
        @Id
        Long userId,
        String email,
        String password,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        String profilePicture,
        LocalDateTime createdOn,
        LocalDateTime lastLogin,
        String role
) {
}
