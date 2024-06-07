package com.stefandragomiroiu.rideshare_backend.controller;

import com.stefandragomiroiu.rideshare_backend.dto.RideDto;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

//    @GetMapping("/findBy")
//    public List<RideWithLocationsAndDriver> findBy(
//            @RequestParam Long departure,
//            @RequestParam Long arrival,
//            @RequestParam LocalDate date,
//            @RequestParam Integer seats
//    ) {
//        return rideService.findBy(departure, arrival, date, seats);
//    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    public Ride publish(@Valid @RequestBody RideDto rideDto) {
        return rideService.publish(rideDto);
    }
}
