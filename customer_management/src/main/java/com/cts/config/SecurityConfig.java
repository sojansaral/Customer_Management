package com.cts.config;

import com.cts.security.JwtAuthenticationEntryPoint;
import com.cts.security.JWTAuthenticationFilter;
import com.cts.security.JwtAccessDeniedHandler;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Security configuration for managing authentication and authorization 
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // Constructor to inject security-related dependencies 
    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // Configures security settings, including authentication and authorization 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Disables CSRF protection for stateless authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**","/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                // Allows authentication endpoints
                .anyRequest().authenticated() // Requires authentication for all other requests
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Handles 401 Unauthorized errors
                .accessDeniedHandler(jwtAccessDeniedHandler) // Handles 403 Forbidden errors
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Uses stateless session management
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Adds JWT authentication filter
            .build();
    }

    // Provides authentication manager for handling authentication requests 
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Provides password encoder for securely hashing passwords 
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
