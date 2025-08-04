package com.profile.service.config;

import com.profile.service.util.Constants;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the Profile Service.
 * - Configures stateless JWT-based authentication
 * - Applies role-based access control for secured endpoints
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures HTTP security including:
     * - CSRF disabled (since JWT is stateless)
     * - Stateless session management
     * - Public and secured route mappings
     * - JWT authentication filter
     * - Unauthorized response handling
     *
     * @param http HttpSecurity object
     * @return configured SecurityFilterChain
     * @throws Exception if security config fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/profile/public/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers("/api/profile/creator/**").hasRole(Constants.CREATOR_ROLE)
                        .requestMatchers("/api/profile/user/**").hasRole(Constants.USER_ROLE)
                        .requestMatchers("/api/profile/admin/**").hasRole(Constants.ADMIN_ROLE)

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())
                        )
                );

        return http.build();
    }

    /**
     * Exposes AuthenticationManager bean to be used in auth services.
     *
     * @param config AuthenticationConfiguration injected by Spring
     * @return AuthenticationManager
     * @throws Exception if configuration fails
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
