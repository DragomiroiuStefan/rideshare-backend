package com.stefandragomiroiu.rideshare_backend.mapper;

import com.stefandragomiroiu.rideshare_backend.dto.RideDto;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RideMapper {
    Ride toEntity(RideDto rideDto);
}
