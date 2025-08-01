package com.profile.service.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // ✅ Key Generator
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Extract userId from token
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ Extract role from token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ✅ Validate token: check expiry & structure
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            boolean expired = claims.getExpiration().before(new Date());

            if (expired) {
                System.out.println("❌ Token expired at: " + claims.getExpiration());
            }

            return !expired;
        } catch (JwtException e) {
            System.out.println("❌ Invalid JWT: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("❌ Token parsing error: " + e.getMessage());
            return false;
        }
    }

    // ✅ Internal method to extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Generate new token
    public String generateToken(String userId, String role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // e.g., 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
