package com.stefandragomiroiu.rideshare_backend.model.projection;

public record ConnectionWithBookedSeats(
        Long connectionId,
        Integer price,
        Integer bookedSeats
) {
}
