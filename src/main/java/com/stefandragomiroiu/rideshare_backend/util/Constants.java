package com.stefandragomiroiu.rideshare_backend.util;

public class Constants {

    public static final long JWT_EXPIRATION_TIME = 900_000; // 15 minutes
    public static final String JWT_ROLE_CLAIM = "role";
    public static final int REFRESH_TOKEN_LENGTH = 128;

    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "User with ID %d not found";
    public static final String VEHICLE_NOT_FOUND_ERROR_MESSAGE = "Vehicle %s not found";
    public static final String LOCATION_NOT_FOUND_ERROR_MESSAGE = "Location with ID %d not found";

    private Constants() {}

}
