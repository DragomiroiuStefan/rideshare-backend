package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Booking(
        @Id
         Long bookingId,
         Long userId,
         Integer adults,
         Integer children,
         Boolean confirmed,
         LocalDateTime bookedAt
) {
}
