package com.stefandragomiroiu.rideshare_backend.model.projection;

import com.stefandragomiroiu.rideshare_backend.model.RideStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RideWithDepartureAndArrival(
         Long rideId,
         Long driver,
         LocalDate departureDate,
         Integer seats,
         String additionalComment,
         String vehicle,
         RideStatus status,
         LocalDateTime postedAt,
         LocalDateTime departureTime,
         LocalDateTime arrivalTime
) {
}
