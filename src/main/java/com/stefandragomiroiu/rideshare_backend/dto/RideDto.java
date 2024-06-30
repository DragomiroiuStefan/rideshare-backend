package com.stefandragomiroiu.rideshare_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.List;

public record RideDto(
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
        // Check connections are linked
        for (int i = 0; i < connections.size() - 1; i++) {
            if (!connections.get(i).arrivalLocation().equals(connections.get(i + 1).departureLocation())) {
                throw new IllegalArgumentException(
                        String.format("Connection %d and %d don't meet", i, i + 1)
                );
            }
        }
        // Check for cycle
        var departures = new HashSet<>();
        for (var connection : connections) {
            departures.add(connection.departureLocation());
            if (departures.contains(connection.arrivalLocation())) {
                throw new IllegalArgumentException("Cannot publish a cyclic ride");
            }
        }
    }
}
