package com.stefandragomiroiu.rideshare_backend.model;

import java.time.LocalDateTime;

public record RideRating(
        Long rideId,
        Long userId,
        Integer rating,
        String comment,
        LocalDateTime postedAt
) {
}
