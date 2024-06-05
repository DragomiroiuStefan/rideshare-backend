package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;

public record UserToken(
        @Id
        Long tokenId,
        String refreshToken,
        Long userId
) {
    public UserToken(String refreshToken, Long userId) {
        this(null, refreshToken, userId);
    }
}
