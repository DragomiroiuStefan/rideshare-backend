package com.stefandragomiroiu.rideshare_backend.controller;

import com.stefandragomiroiu.rideshare_backend.dto.BookingDto;
import com.stefandragomiroiu.rideshare_backend.model.Booking;
import com.stefandragomiroiu.rideshare_backend.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking create(@Valid @RequestBody BookingDto bookingDto) {
        return bookingService.create(bookingDto);
    }

    @PostMapping("/{bookingId}/confirm")
    public Booking confirm(@PathVariable Long bookingId) {
        return bookingService.confirm(bookingId);
    }

    @PostMapping("/{bookingId}/decline")
    public Booking decline(@PathVariable Long bookingId) {
        return bookingService.decline(bookingId);
    }

    @PostMapping("/{bookingId}/cancel")
    public Booking cancel(@PathVariable Long bookingId) {
        return bookingService.cancel(bookingId);
    }
}
