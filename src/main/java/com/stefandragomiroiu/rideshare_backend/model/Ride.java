package com.stefandragomiroiu.rideshare_backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Ride(
        Long rideId,
        Long driver,
        LocalDate departureDate,
        Integer seats,
        String additionalComment,
        String vehicle,
        RideStatus status,
        LocalDateTime postedAt
) {
}
