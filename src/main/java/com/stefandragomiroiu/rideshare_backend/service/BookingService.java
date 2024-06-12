package com.stefandragomiroiu.rideshare_backend.service;

import com.stefandragomiroiu.rideshare_backend.controller.exception.ResourceNotFoundException;
import com.stefandragomiroiu.rideshare_backend.dto.BookingDto;
import com.stefandragomiroiu.rideshare_backend.mapper.BookingMapper;
import com.stefandragomiroiu.rideshare_backend.model.*;
import com.stefandragomiroiu.rideshare_backend.repository.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.stefandragomiroiu.rideshare_backend.util.Constants.*;

@Transactional
@Service
public class BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final BookingConnectionsRepository bookingConnectionsRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final LocationRepository locationRepository;

    public BookingService(BookingMapper bookingMapper, BookingRepository bookingRepository, BookingConnectionsRepository bookingConnectionsRepository, UserRepository userRepository, RideRepository rideRepository, LocationRepository locationRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.bookingConnectionsRepository = bookingConnectionsRepository;
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public Booking create(BookingDto bookingDto) {
        if (userRepository.findById(bookingDto.userId()).isEmpty()) {
            throw new ResourceNotFoundException(String.format(USER_NOT_FOUND_ERROR_MESSAGE, bookingDto.userId()));
        }
        if (locationRepository.findById(bookingDto.departureLocation()).isEmpty()) {
            throw new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, bookingDto.departureLocation()));
        }
        if (locationRepository.findById(bookingDto.arrivalLocation()).isEmpty()) {
            throw new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_ERROR_MESSAGE, bookingDto.arrivalLocation()));
        }
        var ride = rideRepository.findRideBy(bookingDto.rideId(), bookingDto.departureLocation(), bookingDto.arrivalLocation()).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("No ride that goes from location %s to location %s found", bookingDto.departureLocation(), bookingDto.arrivalLocation())
                )
        );
        if (!ride.status().equals(RideStatus.ACTIVE)) {
            throw new IllegalArgumentException(
                    String.format("Ride %d is not active anymore", bookingDto.rideId())
            );
        }
        // Check if there are  seats available
        var connectionsWithBookedSeats = bookingRepository.findConnectionsWithBookedSeats(ride.rideId(), ride.departureTime(), ride.arrivalTime());
        var rideHasAvailableSeats = connectionsWithBookedSeats.stream()
                .anyMatch(c -> {
                    var bookedSeats = c.bookedSeats() != null ? c.bookedSeats() : 0;
                    return ride.seats() - bookedSeats >= bookingDto.adults() + bookingDto.children();
                });  // Connection does not have available seats
        if (!rideHasAvailableSeats) {
            throw new IllegalArgumentException("No available seats for ride " + bookingDto.rideId());
        }

        var booking = bookingMapper.toEntity(bookingDto);
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookedAt(LocalDateTime.now());
        bookingRepository.save(booking);

        // Save booking connections
        var bookingConnections = new ArrayList<BookingConnection>();
        for (var connection : connectionsWithBookedSeats) {
            var bookingConnection = new BookingConnection();
            bookingConnection.setBookingId(AggregateReference.to(booking.getBookingId()));
            bookingConnection.setConnectionId(AggregateReference.to(connection.connectionId()));
            bookingConnections.add(bookingConnection);
        }
        bookingConnectionsRepository.saveAll(bookingConnections);

        return booking;
    }

    public void confirm(Long bookingId) {
        // TODO
    }

    public void cancel(Long bookingId) {
        // TODO
    }

    public void reject(Long bookingId) {
        // TODO
    }
}
