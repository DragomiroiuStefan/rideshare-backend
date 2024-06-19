package com.stefandragomiroiu.rideshare_backend.dto;

public record RideRatingDto(
        Long rideId,
        Integer rating,
        String comment
) {
}
