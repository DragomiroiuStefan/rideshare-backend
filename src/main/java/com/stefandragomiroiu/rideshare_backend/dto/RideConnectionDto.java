package com.stefandragomiroiu.rideshare_backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RideConnectionDto(
        @NotNull
        Long departureLocation,
        @NotNull
        Long arrivalLocation,
        @Future
        LocalDateTime departureTime,
        @Future
        LocalDateTime arrivalTime,
        @Min(1)
        @Max(999)
        Integer price
) {
    public RideConnectionDto {
        if (!arrivalTime.isAfter(departureTime)) {
            throw new IllegalArgumentException(
                    String.format("Departure time %s must be after arrival time %s", departureTime, arrivalTime)
            );
        }
        if (departureLocation.equals(arrivalLocation)) {
            throw new IllegalArgumentException(
                    String.format("Departure location %d and arrival location %d must be different", departureLocation, arrivalLocation)
            );
        }
    }
}
