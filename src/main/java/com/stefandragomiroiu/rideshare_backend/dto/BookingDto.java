package com.stefandragomiroiu.rideshare_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record BookingDto(
        @NotNull
        Long userId,
        @NotNull
        Long rideId,
        @NotNull
        Long departureLocation,
        @NotNull
        Long arrivalLocation,
        @Positive
        Integer seats
) {
    public BookingDto {
            if (departureLocation.equals(arrivalLocation)) {
                throw new IllegalArgumentException("Departure and arrival cannot be the same");
            }
        }
}
