package com.profile.service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtAuthenticationFilter is responsible for:
 * - Intercepting incoming HTTP requests
 * - Extracting and validating JWT tokens from Authorization headers
 * - Setting the authenticated user in the Spring Security context
 *
 * It runs once per request using Spring's OncePerRequestFilter.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Utility class for handling JWT operations such as validation and claim extraction.
     */
    private final JwtUtil jwtUtil;

    /**
     * Filters each request to check for JWT token and sets authentication if token is valid.
     *
     * @param request  incoming HTTP request
     * @param response HTTP response
     * @param filterChain Spring's filter chain
     * @throws ServletException in case of servlet error
     * @throws IOException in case of I/O error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String uri = request.getRequestURI();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("Incoming request: URI={}, Authorization Header={}", uri, authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Authorization header missing or not Bearer format");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        log.debug("Extracted JWT Token: {}", token);

        if (jwtUtil.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {

            String userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractRole(token);

            log.info("Valid JWT token: userId={}, role={}", userId, role);

            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + role)
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    new User(userId, "", authorities),
                    null,
                    authorities
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.warn("Invalid token or user already authenticated");
        }
        filterChain.doFilter(request, response);
    }
}
