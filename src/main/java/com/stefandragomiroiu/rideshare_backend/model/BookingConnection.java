package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public class BookingConnection {
    @Id
    private Long bookingConnectionId;
    private AggregateReference<Booking, Long> bookingId;
    private AggregateReference<RideConnection, Long> connectionId;

    public BookingConnection() {
    }

    public BookingConnection(Long bookingConnectionId, AggregateReference<Booking, Long> bookingId, AggregateReference<RideConnection, Long> connectionId) {
        this.bookingConnectionId = bookingConnectionId;
        this.bookingId = bookingId;
        this.connectionId = connectionId;
    }

    public Long getBookingConnectionId() {
        return bookingConnectionId;
    }

    public void setBookingConnectionId(Long bookingConnectionId) {
        this.bookingConnectionId = bookingConnectionId;
    }

    public AggregateReference<Booking, Long> getBookingId() {
        return bookingId;
    }

    public void setBookingId(AggregateReference<Booking, Long> bookingId) {
        this.bookingId = bookingId;
    }

    public AggregateReference<RideConnection, Long> getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(AggregateReference<RideConnection, Long> connectionId) {
        this.connectionId = connectionId;
    }
}
