package com.profile.service.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * JwtUtil is a utility component responsible for:
 * - Generating JWT tokens
 * - Validating tokens
 * - Extracting user ID and role from JWT claims
 *
 * This class uses HMAC SHA-256 algorithm for signing tokens.
 */
@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Returns the signing key used for JWT creation and validation.
     *
     * @return HMAC SHA key
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Extracts the user ID (subject) from the JWT token.
     *
     * @param token JWT token
     * @return user ID as a string
     */
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts the user role from the JWT token claims.
     *
     * @param token JWT token
     * @return role as a string
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Validates the JWT token by checking its expiration and structure.
     *
     * @param token JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            boolean expired = claims.getExpiration().before(new Date());

            if (expired) {
                log.warn("JWT expired at: {}", claims.getExpiration());
            }

            return !expired;
        } catch (JwtException e) {
            log.error("Invalid JWT: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Token parsing error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Generates a JWT token with user ID and role.
     *
     * @param userId user identifier
     * @param role user role
     * @return signed JWT token as string
     */
    public String generateToken(String userId, String role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token JWT token
     * @return Claims object
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
