package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDateTime;

public class BookingStatusHistory {
    @Id
    private Long bookingStatusHistoryId;
    private AggregateReference<Booking, Long> bookingId;
    private BookingStatus status;
    private LocalDateTime updatedAt;

    public Long getBookingStatusHistoryId() {
        return bookingStatusHistoryId;
    }

    public void setBookingStatusHistoryId(Long bookingStatusHistoryId) {
        this.bookingStatusHistoryId = bookingStatusHistoryId;
    }

    public AggregateReference<Booking, Long> getBookingId() {
        return bookingId;
    }

    public void setBookingId(AggregateReference<Booking, Long> bookingId) {
        this.bookingId = bookingId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
