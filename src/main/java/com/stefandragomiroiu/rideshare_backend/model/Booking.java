package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDateTime;

public class Booking {
    @Id
    private Long bookingId;
    private AggregateReference<User, Long> userId;
    private AggregateReference<Ride, Long> rideId;
    private Integer adults;
    private Integer children;
    private BookingStatus status;
    private LocalDateTime bookedAt;
    private LocalDateTime statusUpdatedAt;

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

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    public LocalDateTime getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }
}
