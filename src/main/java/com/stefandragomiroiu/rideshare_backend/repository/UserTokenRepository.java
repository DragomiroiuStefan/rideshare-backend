package com.stefandragomiroiu.rideshare_backend.repository;

import com.stefandragomiroiu.rideshare_backend.model.UserToken;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends ListCrudRepository<UserToken, Long> {

    @Modifying
    @Query("delete from user_token ut where ut.refresh_token = :refreshToken")
    void deleteByRefreshToken(@Param("refreshToken") String refreshToken);

    @Modifying
    @Query("delete from user_token ut where ut.user_id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    Optional<UserToken> findByRefreshToken(String refreshToken);
}
