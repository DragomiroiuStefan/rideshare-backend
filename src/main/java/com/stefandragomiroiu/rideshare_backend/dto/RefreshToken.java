package com.stefandragomiroiu.rideshare_backend.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshToken (
        @NotBlank
        String refreshToken
){
}
