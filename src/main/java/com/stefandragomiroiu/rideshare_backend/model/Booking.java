package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public class Booking {
    @Id
    private Long bookingId;
    private AggregateReference<User, Long> userId;
    private AggregateReference<Ride, Long> rideId;
    private Integer seats;
    private BookingStatus status;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public AggregateReference<User, Long> getUserId() {
        return userId;
    }

    public void setUserId(AggregateReference<User, Long> userId) {
        this.userId = userId;
    }

    public AggregateReference<Ride, Long> getRideId() {
        return rideId;
    }

    public void setRideId(AggregateReference<Ride, Long> rideId) {
        this.rideId = rideId;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
