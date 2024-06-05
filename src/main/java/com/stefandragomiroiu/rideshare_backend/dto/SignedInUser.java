package com.stefandragomiroiu.rideshare_backend.dto;

import com.stefandragomiroiu.rideshare_backend.model.User;

public record SignedInUser(
        String accessToken,
        String refreshToken,
        User user
) {
}
