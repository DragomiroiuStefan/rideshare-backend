package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ride {
    @Id
    private Long rideId;
    private Long driver;
    private LocalDate departureDate;
    private Integer seats;
    private String additionalComment;
    private String vehicle;
    private RideStatus status;
    private LocalDateTime postedAt;

    public Ride(Long rideId, Long driver, LocalDate departureDate, Integer seats, String additionalComment, String vehicle, RideStatus status, LocalDateTime postedAt) {
        this.rideId = rideId;
        this.driver = driver;
        this.departureDate = departureDate;
        this.seats = seats;
        this.additionalComment = additionalComment;
        this.vehicle = vehicle;
        this.status = status;
        this.postedAt = postedAt;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Long getDriver() {
        return driver;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }
}
