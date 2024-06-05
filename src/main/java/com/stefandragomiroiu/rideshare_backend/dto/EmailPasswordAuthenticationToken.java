package com.stefandragomiroiu.rideshare_backend.dto;

public record EmailPasswordAuthenticationToken (
        String email,
        String password
) {
}
