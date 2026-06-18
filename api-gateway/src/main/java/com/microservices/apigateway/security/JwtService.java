package com.microservices.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12345";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes());

    public boolean validateToken(
            String token) {

        try {

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception ex) {

            return false;
        }
    }

    public String extractEmail(
            String token) {

        Claims claims =
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claims.getSubject();
    }

    public String
    extractRole(
            String token
    ) {

        Claims claims =
                Jwts.parser()
                        .verifyWith(
                                key
                        )
                        .build()
                        .parseSignedClaims(
                                token
                        )
                        .getPayload();

        return claims.get(
                "role",
                String.class
        );
    }
}