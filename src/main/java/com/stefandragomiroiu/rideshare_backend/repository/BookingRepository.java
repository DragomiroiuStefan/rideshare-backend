package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.Booking;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.User;
import com.stefandragomiroiu.rideshare_backend.model.projection.ConnectionWithBookedSeats;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends ListCrudRepository<Booking, Long> {

    @Query("""
            select rc.connection_id, rc.price, sum(b.seats) as booked_seats
            from ride_connection rc
                     left join booking_connection bc on rc.connection_id = bc.connection_id
                     left join booking b on b.booking_id = bc.booking_id and b.status = 'CONFIRMED'
            where rc.ride_id = :rideId
              and rc.departure_time >= :departureTime
              and rc.arrival_time <= :arrivalTime
            group by rc.connection_id;
            """)
    List<ConnectionWithBookedSeats> findConnectionsWithBookedSeats(Long rideId, LocalDateTime departureTime, LocalDateTime arrivalTime);

    @Query("""
            select bc.connection_id
            from booking b
            inner join public.booking_connection bc on b.booking_id = bc.booking_id
            where b.booking_id = :bookingId;
            """)
    List<Long> findConnectionsById(Long bookingId);

    @Query("""
            select rc.connection_id, rc.price, sum(b.seats) as booked_seats
            from ride_connection rc
                     left join booking_connection bc on rc.connection_id = bc.connection_id
                     left join booking b on b.booking_id = bc.booking_id and b.status = 'CONFIRMED'
            where rc.connection_id in (:connectionIds)
            group by rc.connection_id;
            """)
    List<ConnectionWithBookedSeats> findConnectionsWithBookedSeats(List<Long> connectionIds);

    Optional<Booking> findByUserIdAndRideIdAndStatus(Long userId, Long rideId, String status);
}
