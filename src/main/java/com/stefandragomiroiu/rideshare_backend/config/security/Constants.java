package com.stefandragomiroiu.rideshare_backend.config.security;

public class Constants {
    public static final String SIGN_IN_URL = "/auth/signIn";
    public static final String SIGN_UP_URL = "/auth/signUp";
    public static final String REFRESH_URL = "/auth/refresh";

    public static final long JWT_EXPIRATION_TIME = 900_000; // 15 minutes
    public static final String JWT_ROLE_CLAIM = "role";
    public static final int REFRESH_TOKEN_LENGTH = 128;

    private Constants() {}

}
