package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.BookingConnection;
import com.stefandragomiroiu.rideshare_backend.model.BookingStatusHistory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatusHistoryRepository extends ListCrudRepository<BookingStatusHistory, Long> {
}
