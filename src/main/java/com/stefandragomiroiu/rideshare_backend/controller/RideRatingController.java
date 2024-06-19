package com.stefandragomiroiu.rideshare_backend.controller;

import com.stefandragomiroiu.rideshare_backend.dto.RideRatingDto;
import com.stefandragomiroiu.rideshare_backend.model.RideRating;
import com.stefandragomiroiu.rideshare_backend.service.RideRatingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride-ratings")
public class RideRatingController {

    private static final Logger logger = LoggerFactory.getLogger(RideRatingController.class);
    private final RideRatingService rideRatingService;

    public RideRatingController(RideRatingService rideRatingService) {
        this.rideRatingService = rideRatingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RideRating create(@Valid @RequestBody RideRatingDto rideRatingDto) {
        logger.info("Create request for ride rating: {}", rideRatingDto);
        return rideRatingService.create(rideRatingDto);
    }
}
