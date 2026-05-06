package com.smartfinance.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private static final String SECRET =
            "mysupersecretkeymysupersecretkey";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes()
            );

    // ✅ VALIDATE TOKEN
    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException e) {

            return false;
        }
    }

    // ✅ EXTRACT ALL CLAIMS
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ EXTRACT USER EMAIL
    public String extractUser(String token) {

        return extractClaims(token)
                .getSubject();
    }

    // ✅ EXTRACT ROLE
    public String extractRole(String token) {

        return extractClaims(token)
                .get("role", String.class);
    }
}