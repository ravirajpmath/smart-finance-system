package com.smartfinance.userservice.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET =
            "mysupersecretkeymysupersecretkey";

    private final Key key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes()
            );

    public String generateToken(
            String email,
            String role) {

        return Jwts.builder()

                .subject(email)

                // ✅ ADD ROLE CLAIM
                .claim("role", role)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000
                        )
                )

                .signWith(key)

                .compact();
    }
}