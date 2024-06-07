package com.stefandragomiroiu.rideshare_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record RideDto(
        @NotNull
        Long driver,
        @Future
        LocalDate departureDate,
        @Min(1)
        @Max(10)
        Integer seats,
        String additionalComment,
        @NotBlank
        String vehicle,
        @NotEmpty
        List<@Valid RideConnectionDto> connections
) {
    public RideDto {
        for (int i = 0; i < connections.size() - 1; i++) {
            if (!connections.get(i).arrivalLocation().equals(connections.get(i + 1).departureLocation())) {
                throw new IllegalArgumentException(
                        String.format("Connection %d and %d don't meet", i, i + 1)
                );
            }
        }
        // TODO No cycle validation
    }
}
