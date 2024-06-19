package com.stefandragomiroiu.rideshare_backend.service;

import com.stefandragomiroiu.rideshare_backend.controller.exception.ResourceNotFoundException;
import com.stefandragomiroiu.rideshare_backend.dto.RideRatingDto;
import com.stefandragomiroiu.rideshare_backend.mapper.RideRatingMapper;
import com.stefandragomiroiu.rideshare_backend.model.BookingStatus;
import com.stefandragomiroiu.rideshare_backend.model.RideRating;
import com.stefandragomiroiu.rideshare_backend.model.RideStatus;
import com.stefandragomiroiu.rideshare_backend.repository.BookingRepository;
import com.stefandragomiroiu.rideshare_backend.repository.RideRatingRepository;
import com.stefandragomiroiu.rideshare_backend.repository.RideRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.stefandragomiroiu.rideshare_backend.util.Constants.RIDE_NOT_FOUND_ERROR_MESSAGE;

@Service
@Transactional
public class RideRatingService {

    private final RideRatingMapper mapper;
    private final RideRatingRepository rideRatingRepository;
    private final RideRepository rideRepository;
    private final BookingRepository bookingRepository;

    public RideRatingService(RideRatingMapper mapper, RideRepository rideRepository, RideRatingRepository rideRatingRepository, BookingRepository bookingRepository) {
        this.mapper = mapper;
        this.rideRepository = rideRepository;
        this.rideRatingRepository = rideRatingRepository;
        this.bookingRepository = bookingRepository;
    }

    public RideRating create(RideRatingDto rideRatingDto) {
        var ride = rideRepository.findById(rideRatingDto.rideId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format(RIDE_NOT_FOUND_ERROR_MESSAGE, rideRatingDto.rideId())
                )
        );
        if (!ride.getStatus().equals(RideStatus.FINISHED)) {
            throw new IllegalArgumentException(
                    String.format("Ride %d has not finished yet.", rideRatingDto.rideId())
            );
        }
        long userId = 2L;
        bookingRepository.findByUserIdAndRideIdAndStatus(
                userId, // TODO get Principal
                rideRatingDto.rideId(),
                BookingStatus.CONFIRMED.toString()
        ).orElseThrow(() -> new IllegalArgumentException(
                String.format("Booking not found for user %d and ride %d", userId, rideRatingDto.rideId())
        ));

        RideRating rideRating = mapper.toEntity(rideRatingDto);
        rideRating.setPostedAt(LocalDateTime.now());
        rideRating.setUserId(AggregateReference.to(userId)); //TODO get Principal
        return rideRatingRepository.save(rideRating);
    }
}
