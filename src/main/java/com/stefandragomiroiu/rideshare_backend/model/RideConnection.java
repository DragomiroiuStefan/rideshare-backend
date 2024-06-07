package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDateTime;

public class RideConnection {
    @Id
    private Long connectionId;
    private Long departureLocation;
    private Long arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer price;
    private AggregateReference<Ride, Long> rideId;

    public RideConnection(Long connectionId, Long departureLocation, Long arrivalLocation, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer price, AggregateReference<Ride, Long> rideId) {
        this.connectionId = connectionId;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.rideId = rideId;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

    public Long getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(Long departureLocation) {
        this.departureLocation = departureLocation;
    }

    public Long getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(Long arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public AggregateReference<Ride, Long> getRideId() {
        return rideId;
    }

    public void setRideId(AggregateReference<Ride, Long> rideId) {
        this.rideId = rideId;
    }
}
