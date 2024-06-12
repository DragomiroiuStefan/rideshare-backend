package com.stefandragomiroiu.rideshare_backend.mapper;

import com.stefandragomiroiu.rideshare_backend.dto.BookingDto;
import com.stefandragomiroiu.rideshare_backend.model.Booking;
import com.stefandragomiroiu.rideshare_backend.model.Location;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.User;
import org.mapstruct.Mapper;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toEntity(BookingDto bookingDto);

    default AggregateReference<User,Long> toUserAggregateReference(Long userId) {
        return AggregateReference.to(userId);
    }

    default AggregateReference<Ride,Long> toRideAggregateReference(Long rideId) {
        return AggregateReference.to(rideId);
    }

    default AggregateReference<Location,Long> toLocationAggregateReference(Long locationId) {
        return AggregateReference.to(locationId);
    }
}
