package com.stefandragomiroiu.rideshare_backend.service;

import com.stefandragomiroiu.rideshare_backend.controller.exception.ResourceNotFoundException;
import com.stefandragomiroiu.rideshare_backend.dto.BookingDto;
import com.stefandragomiroiu.rideshare_backend.mapper.BookingMapper;
import com.stefandragomiroiu.rideshare_backend.model.*;
import com.stefandragomiroiu.rideshare_backend.model.projection.ConnectionWithBookedSeats;
import com.stefandragomiroiu.rideshare_backend.model.projection.RideWithDepartureAndArrival;
import com.stefandragomiroiu.rideshare_backend.repository.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.stefandragomiroiu.rideshare_backend.util.Constants.*;

@Transactional
@Service
public class BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final BookingConnectionsRepository bookingConnectionsRepository;
    private final BookingStatusHistoryRepository bookingStatusHistoryRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final LocationRepository locationRepository;

    public BookingService(BookingMapper bookingMapper, BookingRepository bookingRepository, BookingConnectionsRepository bookingConnectionsRepository, BookingStatusHistoryRepository bookingStatusHistoryRepository, UserRepository userRepository, RideRepository rideRepository, LocationRepository locationRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.bookingConnectionsRepository = bookingConnectionsRepository;
        this.bookingStatusHistoryRepository = bookingStatusHistoryRepository;
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.locationRepository = locationRepository;
    }

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
        var rideHasAvailableSeats = hasAvailableSeats(connectionsWithBookedSeats, ride.seats(), bookingDto.seats());
        if (!rideHasAvailableSeats) {
            throw new IllegalArgumentException("No available seats for ride " + bookingDto.rideId());
        }

        var booking = bookingMapper.toEntity(bookingDto);
        booking.setStatus(BookingStatus.PENDING);
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

        // Add booking status history entry
        var bookingStatusHistory = new BookingStatusHistory();
        bookingStatusHistory.setBookingId(AggregateReference.to(booking.getBookingId()));
        bookingStatusHistory.setStatus(BookingStatus.PENDING);
        bookingStatusHistoryRepository.save(bookingStatusHistory);

        return booking;
    }

    public Booking confirm(Long bookingId) {
        var driver = 2; // TODO
        var booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(BOOKING_NOT_FOUND_ERROR_MESSAGE, bookingId)));
        if (!booking.getStatus().equals(BookingStatus.PENDING)) {
            throw new IllegalArgumentException(
                    String.format("Booking %d is not PENDING", bookingId)
            );
        }
        var ride = rideRepository.findById(booking.getRideId().getId()).orElseThrow(() -> new IllegalStateException(
                String.format("Ride not found for booking %d", bookingId)
        ));
        if (!ride.getStatus().equals(RideStatus.ACTIVE)) {
            throw new IllegalArgumentException(
                    String.format("Ride %d is not active anymore", ride.getRideId())
            );
        }
        // Check if the ride still has available seats. (Other bookings may have been confirmed in the meantime)
        List<Long> connections = bookingRepository.findConnectionsById(bookingId);
        var connectionsWithBookedSeats = bookingRepository.findConnectionsWithBookedSeats(connections);
        var rideHasAvailableSeats = hasAvailableSeats(connectionsWithBookedSeats, ride.getSeats(), booking.getSeats());
        if (!rideHasAvailableSeats) {
            throw new IllegalArgumentException("No available seats for ride " + ride.getRideId());
        }

        updateBookingStatus(booking, BookingStatus.CONFIRMED);

        return booking;
    }

    public Booking decline(Long bookingId) {
        var driver = 2; // TODO
        var booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(BOOKING_NOT_FOUND_ERROR_MESSAGE, bookingId)));
        if (!booking.getStatus().equals(BookingStatus.PENDING)) {
            throw new IllegalArgumentException(
                    String.format("Booking %d is not PENDING", bookingId)
            );
        }
        updateBookingStatus(booking, BookingStatus.DECLINED);

        return booking;
    }

    public Booking cancel(Long bookingId) {
        var passenger = 3L; // TODO
        var booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(BOOKING_NOT_FOUND_ERROR_MESSAGE, bookingId)));
        if (!booking.getStatus().equals(BookingStatus.PENDING) || !booking.getStatus().equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException(
                    String.format("Booking %d is not PENDING or CONFIRMED", bookingId)
            );
        }
        if (!booking.getUserId().getId().equals(passenger)) {
            throw new IllegalArgumentException(
                    String.format("Booking %d was not booked by %d", bookingId, passenger)
            );
        }
        updateBookingStatus(booking, BookingStatus.CANCELED);

        return booking;
    }

    private void updateBookingStatus(Booking booking, BookingStatus status) {
        // Update booking status
        booking.setStatus(status);
        bookingRepository.save(booking);

        // Add booking status history entry
        var bookingStatusHistory = new BookingStatusHistory();
        bookingStatusHistory.setBookingId(AggregateReference.to(booking.getBookingId()));
        bookingStatusHistory.setStatus(status);
        bookingStatusHistory.setUpdatedAt(LocalDateTime.now());
        bookingStatusHistoryRepository.save(bookingStatusHistory);
    }

    /**
     * Functional
     * Check if connections on a ride have available seats
     * @param connectionsWithBookedSeats The list of connections with the number of booked seats for each
     * @param availableSeats The number of available seats on the ride
     * @param requestedSeats The number of requested seats
     */
    private static boolean hasAvailableSeats(List<ConnectionWithBookedSeats> connectionsWithBookedSeats, int availableSeats, int requestedSeats) {
        return connectionsWithBookedSeats.stream()
                .anyMatch(c -> {
                    var bookedSeats = c.bookedSeats() != null ? c.bookedSeats() : 0;
                    return availableSeats - bookedSeats >= requestedSeats;
                });
    }
}
