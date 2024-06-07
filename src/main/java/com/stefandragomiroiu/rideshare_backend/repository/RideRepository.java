package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.Location;
import com.stefandragomiroiu.rideshare_backend.model.Ride;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends ListCrudRepository<Ride, Long> {
}
