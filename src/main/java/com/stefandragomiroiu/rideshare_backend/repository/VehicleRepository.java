package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.Ride;
import com.stefandragomiroiu.rideshare_backend.model.Vehicle;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends ListCrudRepository<Vehicle, String> {
}
