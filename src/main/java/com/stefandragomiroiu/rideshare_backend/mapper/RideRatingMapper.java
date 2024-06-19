package com.stefandragomiroiu.rideshare_backend.mapper;

import com.stefandragomiroiu.rideshare_backend.dto.RideRatingDto;
import com.stefandragomiroiu.rideshare_backend.model.Location;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.RideRating;
import com.stefandragomiroiu.rideshare_backend.model.User;
import org.mapstruct.Mapper;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Mapper(componentModel = "spring")
public interface RideRatingMapper {
    RideRating toEntity(RideRatingDto rideRatingDto);

    default AggregateReference<Ride, Long> toRideAggregateReference(Long rideId) {
        return AggregateReference.to(rideId);
    }

}
