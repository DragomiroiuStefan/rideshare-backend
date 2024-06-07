package com.stefandragomiroiu.rideshare_backend.mapper;

import com.stefandragomiroiu.rideshare_backend.dto.RideConnectionDto;
import com.stefandragomiroiu.rideshare_backend.model.RideConnection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideConnectionsMapper {
    RideConnection toEntity(RideConnectionDto rideConnectionDto);
    List<RideConnection> toEntities(List<RideConnectionDto> rideConnectionDto);
}
