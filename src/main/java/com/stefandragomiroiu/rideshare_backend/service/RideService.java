package com.stefandragomiroiu.rideshare_backend.service;

import com.stefandragomiroiu.rideshare_backend.controller.exception.ResourceNotFoundException;
import com.stefandragomiroiu.rideshare_backend.dto.RideConnectionDto;
import com.stefandragomiroiu.rideshare_backend.dto.RideDto;
import com.stefandragomiroiu.rideshare_backend.mapper.RideConnectionsMapper;
import com.stefandragomiroiu.rideshare_backend.mapper.RideMapper;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.RideRating;
import com.stefandragomiroiu.rideshare_backend.model.RideStatus;
import com.stefandragomiroiu.rideshare_backend.model.Vehicle;
import com.stefandragomiroiu.rideshare_backend.model.projection.RideWithDepartureAndArrival;
import com.stefandragomiroiu.rideshare_backend.repository.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.stefandragomiroiu.rideshare_backend.util.Constants.*;

@Transactional
@Service
public class RideService {

    private final RideMapper rideMapper;
    private final RideConnectionsMapper rideConnectionsMapper;
    private final RideRepository rideRepository;
    private final RideConnectionsRepository rideConnectionsRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;

    public RideService(RideMapper rideMapper, RideConnectionsMapper rideConnectionsMapper, RideRepository rideRepository, RideConnectionsRepository rideConnectionsRepository, BookingRepository bookingRepository, UserRepository userRepository, VehicleRepository vehicleRepository, LocationRepository locationRepository) {
        this.rideMapper = rideMapper;
        this.rideConnectionsMapper = rideConnectionsMapper;
        this.rideRepository = rideRepository;
        this.rideConnectionsRepository = rideConnectionsRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true)
    public List<RideWithDepartureAndArrival> findBy(Long departureLocation, Long arrivalLocation, LocalDate date, Integer seats) {
        if (locationRepository.findById(departureLocation).isEmpty()) {
            throw new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, departureLocation));
        }
        if (locationRepository.findById(arrivalLocation).isEmpty()) {
            throw new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, arrivalLocation));
        }

        // Get rides that go from departure to arrival
        var rides = rideRepository.findRidesBy(departureLocation, arrivalLocation, date);

        // Check if rides found have available seats on all connections
        rides = rides.stream()
                .filter(ride -> hasAvailableSeats(ride, seats))
                .toList();

        // Get driver information
        // TODO

        return rides;
    }

    /**
     * Check if a ride has available seats. Bookings can be made on consecutive connections of a ride,
     * so it is needed to check that all connections of a ride have available seats.
     */
    public boolean hasAvailableSeats(RideWithDepartureAndArrival ride, Integer seats) {
        var connectionsWithBookedSeats = bookingRepository.findConnectionsWithBookedSeats(ride.rideId(), ride.departureTime(), ride.arrivalTime());

        return connectionsWithBookedSeats.stream()
                .anyMatch(c -> {
                    var bookedSeats = c.bookedSeats() != null ? c.bookedSeats() : 0;
                    return ride.seats() - bookedSeats >= seats;
                });   // Connection does not have available seats
    }

    public Ride publish(RideDto rideDto) {
        var driver = 2L; // TODO
        var vehicle = vehicleRepository.findById(rideDto.vehicle()).orElseThrow(
                () -> new ResourceNotFoundException(String.format(VEHICLE_NOT_FOUND_ERROR_MESSAGE, rideDto.vehicle()))
        );
        if (!vehicle.getOwner().equals(driver)) {
            throw new IllegalArgumentException(String.format("Vehicle %s does not belong to user %d", vehicle.getPlateNumber(), driver));
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
        ride.setDriver(driver);
        ride.setDepartureDate(rideDto.connections().getFirst().departureTime().toLocalDate());
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
