package com.stefandragomiroiu.rideshare_backend.service;

import com.stefandragomiroiu.rideshare_backend.controller.exception.ResourceNotFoundException;
import com.stefandragomiroiu.rideshare_backend.dto.RideConnectionDto;
import com.stefandragomiroiu.rideshare_backend.dto.RideDto;
import com.stefandragomiroiu.rideshare_backend.mapper.RideConnectionsMapper;
import com.stefandragomiroiu.rideshare_backend.mapper.RideMapper;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.RideStatus;
import com.stefandragomiroiu.rideshare_backend.repository.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.stefandragomiroiu.rideshare_backend.util.Constants.*;

@Service
public class RideService {

    private final RideMapper rideMapper;
    private final RideConnectionsMapper rideConnectionsMapper;
    private final RideRepository rideRepository;
    private final RideConnectionsRepository rideConnectionsRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;

    public RideService(RideMapper rideMapper, RideConnectionsMapper rideConnectionsMapper, RideRepository rideRepository, RideConnectionsRepository rideConnectionsRepository, UserRepository userRepository, VehicleRepository vehicleRepository, LocationRepository locationRepository) {
        this.rideMapper = rideMapper;
        this.rideConnectionsMapper = rideConnectionsMapper;
        this.rideRepository = rideRepository;
        this.rideConnectionsRepository = rideConnectionsRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public Ride publish(RideDto rideDto) {
        if (userRepository.findById(rideDto.driver()).isEmpty()) {
            throw new ResourceNotFoundException(String.format(USER_NOT_FOUND_ERROR_MESSAGE, rideDto.driver()));
        }
        if (vehicleRepository.findById(rideDto.vehicle()).isEmpty()) {
            throw new ResourceNotFoundException(String.format(VEHICLE_NOT_FOUND_ERROR_MESSAGE, rideDto.vehicle()));
        }
        for (RideConnectionDto connection : rideDto.connections()) {
            if (locationRepository.findById(connection.departureLocation()).isEmpty()) {
                throw new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, connection.departureLocation()));
            }
            if (locationRepository.findById(connection.arrivalLocation()).isEmpty()) {
                throw new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, connection.arrivalLocation()));
            }
        }

        var ride = rideMapper.toEntity(rideDto);
        ride.setStatus(RideStatus.ACTIVE);
        ride.setPostedAt(LocalDateTime.now());
        rideRepository.save(ride);

        var connections = rideConnectionsMapper.toEntities(rideDto.connections());
        for (var rideConnection : connections) {
            rideConnection.setRideId(AggregateReference.to(ride.getRideId()));
        }
        rideConnectionsRepository.saveAll(connections);

        return ride;
    }
}
