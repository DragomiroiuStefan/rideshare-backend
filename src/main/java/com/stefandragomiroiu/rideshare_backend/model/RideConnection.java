package com.stefandragomiroiu.rideshare_backend.model;

import java.time.LocalDateTime;

public record RideConnection(
        Long connectionId,
        Long departureLocation,
        Long arrivalLocation,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String departureAddress,
        String arrivalAddress,
        Integer price,
        Long rideId
) {
}
