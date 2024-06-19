package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDateTime;

public class RideRating {
    @Id
    private Long rideRatingId;
    private AggregateReference<Ride, Long> rideId;
    private AggregateReference<User, Long> userId;
    private Integer rating;
    private String comment;
    private LocalDateTime postedAt;

    public Long getRideRatingId() {
        return rideRatingId;
    }

    public void setRideRatingId(Long rideRatingId) {
        this.rideRatingId = rideRatingId;
    }

    public AggregateReference<Ride, Long> getRideId() {
        return rideId;
    }

    public void setRideId(AggregateReference<Ride, Long> rideId) {
        this.rideId = rideId;
    }

    public AggregateReference<User, Long> getUserId() {
        return userId;
    }

    public void setUserId(AggregateReference<User, Long> userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }
}
