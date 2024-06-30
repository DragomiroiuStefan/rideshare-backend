package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.Location;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends ListCrudRepository<Location, Long> {
    List<Location> findByCityContainingIgnoreCase(String city);
}
