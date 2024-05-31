package com.stefandragomiroiu.rideshare_backend.model;

import java.time.LocalDateTime;

public record Booking(
         Long bookingId,
         Long userId,
         Integer adults,
         Integer children,
         Boolean confirmed,
         LocalDateTime bookedAt
) {
}
