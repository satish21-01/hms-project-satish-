package com.hms.appointment_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    // 🔥 FIXED (NO Base64)
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor("my-super-secret-key-for-hms-project-123456".getBytes());
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return validateToken(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        return validateToken(token).get("roles", List.class);
    }
}