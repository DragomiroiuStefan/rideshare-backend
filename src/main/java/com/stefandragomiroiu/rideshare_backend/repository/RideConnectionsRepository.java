package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.RideConnection;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideConnectionsRepository extends ListCrudRepository<RideConnection, Long> {
    List<RideConnection> findByRideId(Long rideId);
}
