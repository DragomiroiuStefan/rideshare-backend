package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
