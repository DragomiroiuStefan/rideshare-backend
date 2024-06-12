package com.stefandragomiroiu.rideshare_backend.service;

import com.stefandragomiroiu.rideshare_backend.util.Constants;
import com.stefandragomiroiu.rideshare_backend.config.security.JwtManager;
import com.stefandragomiroiu.rideshare_backend.controller.exception.EmailAlreadyUsedException;
import com.stefandragomiroiu.rideshare_backend.controller.exception.InvalidCredentialsException;
import com.stefandragomiroiu.rideshare_backend.controller.exception.InvalidRefreshTokenException;
import com.stefandragomiroiu.rideshare_backend.controller.exception.ResourceNotFoundException;
import com.stefandragomiroiu.rideshare_backend.dto.EmailPasswordAuthenticationToken;
import com.stefandragomiroiu.rideshare_backend.dto.SignedInUser;
import com.stefandragomiroiu.rideshare_backend.model.Role;
import com.stefandragomiroiu.rideshare_backend.model.User;
import com.stefandragomiroiu.rideshare_backend.model.UserToken;
import com.stefandragomiroiu.rideshare_backend.repository.UserRepository;
import com.stefandragomiroiu.rideshare_backend.repository.UserTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import static com.stefandragomiroiu.rideshare_backend.util.Constants.USER_NOT_FOUND_ERROR_MESSAGE;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final JwtManager jwtManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserTokenRepository userTokenRepository, JwtManager jwtManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
        this.jwtManager = jwtManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignedInUser signUp(User user) {
        // TODO move database access code to a transactional method
        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        user = new User(
                null,
                user.email(),
                passwordEncoder.encode(user.password()),
                user.firstName(),
                user.lastName(),
                user.phoneNumber(),
                user.birthDate(),
                null,
                null,
                null,
                Role.USER.toString()
        );

        User savedUser = userRepository.save(user);

        var accessToken = jwtManager.create(savedUser);

        var refreshToken = createRefreshToken();
        userTokenRepository.save(new UserToken(refreshToken, savedUser.userId()));

        return new SignedInUser(
                accessToken,
                refreshToken,
                savedUser
        );
    }

    @Transactional
    public SignedInUser signIn(EmailPasswordAuthenticationToken authentication) {
        Optional<User> optionalUser = userRepository.findByEmail(authentication.email());
        if (optionalUser.isEmpty() || !passwordEncoder.matches(authentication.password(), optionalUser.get().password())) {
            throw new InvalidCredentialsException("Login Failed: Your email or password is incorrect");
        }
        User user = optionalUser.get();

        userTokenRepository.deleteByUserId(user.userId());

        var accessToken = jwtManager.create(user);

        var refreshToken = createRefreshToken();
        userTokenRepository.save(new UserToken(refreshToken, user.userId()));

        return new SignedInUser(
                accessToken,
                refreshToken,
                user
        );
    }

    public void signOut(String refreshToken) {
        userTokenRepository.deleteByRefreshToken(refreshToken);
    }

    public SignedInUser refresh(String refreshToken) {
        UserToken userToken = userTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);

        User user = userRepository.findById(userToken.userId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(USER_NOT_FOUND_ERROR_MESSAGE, userToken.userId())
                ));

        var accessToken = jwtManager.create(user);

        return new SignedInUser(
                accessToken,
                refreshToken,
                user
        );
    }

    private String createRefreshToken() {
        var random = new SecureRandom();
        String val = new BigInteger(Constants.REFRESH_TOKEN_LENGTH * 5 /*base 32,2^5*/, random)
                .toString(32);
        return String.format("%" + Constants.REFRESH_TOKEN_LENGTH + "s", val)
                .replace(' ', '0');
    }

}
