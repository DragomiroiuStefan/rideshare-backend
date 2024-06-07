package com.stefandragomiroiu.rideshare_backend.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stefandragomiroiu.rideshare_backend.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import static com.stefandragomiroiu.rideshare_backend.util.Constants.JWT_EXPIRATION_TIME;
import static com.stefandragomiroiu.rideshare_backend.util.Constants.JWT_ROLE_CLAIM;

@Component
public class JwtManager {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtManager(@Lazy RSAPrivateKey privateKey, @Lazy RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String create(User user) {
        final long now = System.currentTimeMillis();
        return JWT.create()
                .withIssuer("RideShare")
                .withSubject(user.email())
                .withClaim(
                        JWT_ROLE_CLAIM,
                        user.role())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + JWT_EXPIRATION_TIME))
                .sign(Algorithm.RSA256(publicKey, privateKey));
    }
}
