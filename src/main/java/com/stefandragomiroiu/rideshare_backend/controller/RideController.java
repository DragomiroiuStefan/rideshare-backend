package com.stefandragomiroiu.rideshare_backend.controller;

import com.stefandragomiroiu.rideshare_backend.dto.RideDto;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.projection.RideWithDepartureAndArrival;
import com.stefandragomiroiu.rideshare_backend.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping()
    public List<RideWithDepartureAndArrival> findBy(
            @RequestParam Long departureLocation,
            @RequestParam Long arrivalLocation,
            @RequestParam LocalDate date,
            @RequestParam Integer seats
    ) {
        return rideService.findBy(departureLocation, arrivalLocation, date, seats);
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    public Ride publish(@Valid @RequestBody RideDto rideDto) {
        return rideService.publish(rideDto);
    }
}
