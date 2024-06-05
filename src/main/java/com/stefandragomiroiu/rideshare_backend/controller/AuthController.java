package com.stefandragomiroiu.rideshare_backend.controller;

import com.stefandragomiroiu.rideshare_backend.dto.EmailPasswordAuthenticationToken;
import com.stefandragomiroiu.rideshare_backend.dto.RefreshToken;
import com.stefandragomiroiu.rideshare_backend.dto.SignedInUser;
import com.stefandragomiroiu.rideshare_backend.model.User;
import com.stefandragomiroiu.rideshare_backend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public SignedInUser signUp(@RequestBody User user) {
        logger.info("Sign Up request for user: {}", user.userId());
        return userService.signUp(user);
    }

    @PostMapping("/signIn")
    public SignedInUser signIn(@Valid @RequestBody EmailPasswordAuthenticationToken authentication) {
        return userService.signIn(authentication);
    }

    @PostMapping("/signOut")
    public void signOut(@RequestBody RefreshToken refreshToken) {
        userService.signOut(refreshToken.refreshToken());
    }

    @PostMapping("/refresh")
    public SignedInUser refresh(@RequestBody RefreshToken refreshToken) {
        return userService.refresh(refreshToken.refreshToken());
    }

}
