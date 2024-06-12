package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.projection.RideWithDepartureAndArrival;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends ListCrudRepository<Ride, Long> {

    @Query("""
            SELECT r.*,
                   rc_dep.departure_time,
                   rc_arr.arrival_time
            FROM ride r
                     JOIN ride_connection rc_dep ON r.ride_id = rc_dep.ride_id
                     JOIN ride_connection rc_arr ON r.ride_id = rc_arr.ride_id
            WHERE rc_dep.departure_location = :departureLocation
              AND rc_arr.arrival_location = :arrivalLocation
              AND rc_dep.departure_time < rc_arr.arrival_time
              AND r.status = 'ACTIVE'
              AND r.departure_date = :date
            """)
    List<RideWithDepartureAndArrival> findRidesBy(Long departureLocation, Long arrivalLocation, LocalDate date);

    @Query("""
            SELECT r.*,
                   rc_dep.departure_time,
                   rc_arr.arrival_time
            FROM ride r
                     JOIN ride_connection rc_dep ON r.ride_id = rc_dep.ride_id
                     JOIN ride_connection rc_arr ON r.ride_id = rc_arr.ride_id
            WHERE r.ride_id = :rideId
              AND rc_dep.departure_location = :departureLocation
              AND rc_arr.arrival_location = :arrivalLocation
              AND rc_dep.departure_time < rc_arr.arrival_time;
            """)
    Optional<RideWithDepartureAndArrival> findRideBy(Long rideId, Long departureLocation, Long arrivalLocation);
}
