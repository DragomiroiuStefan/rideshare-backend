package com.stefandragomiroiu.rideshare_backend.controller;

import com.stefandragomiroiu.rideshare_backend.controller.exception.ResourceNotFoundException;
import com.stefandragomiroiu.rideshare_backend.controller.validation.Update;
import com.stefandragomiroiu.rideshare_backend.model.Location;
import com.stefandragomiroiu.rideshare_backend.repository.LocationRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/locations")
class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    public static final String LOCATION_NOT_FOUND_ERROR_MESSAGE = "Location with ID %d not found";

    private final LocationRepository locationRepository;

    LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @GetMapping("/findByCityContaining")
    public List<Location> findByCityContaining(@RequestParam String city) {
        return locationRepository.findByCityContaining(city);
    }

    @GetMapping("/{locationId}")
    public Location findById(@PathVariable Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, locationId)
                ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Location create(@Valid @RequestBody Location location) {
        logger.info("Create request for location: {}", location);
        return locationRepository.save(location);
    }

    @PutMapping("/{locationID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Location update(@PathVariable Long locationID, @Validated(Update.class) @RequestBody Location location) {
        logger.info("Update request for location: {}", location);
        if (locationRepository.findById(locationID).isEmpty()) {
            throw new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, locationID));
        }
        return locationRepository.save(location);
    }

    @DeleteMapping("/{locationID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Long locationID) {
        logger.info("Delete request location with ID: {}", locationID);
        locationRepository.deleteById(locationID);
    }
}
