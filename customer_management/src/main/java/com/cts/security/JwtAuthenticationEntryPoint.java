package com.cts.security;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
 
import java.io.IOException;

// Entry point for handling unauthorized access in JWT-based authentication 
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Handles authentication failures and returns appropriate HTTP response
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        String authHeader = request.getHeader("Authorization");
        String path = request.getRequestURI();

        // Handles missing or empty authorization header
        if (!(path.equals("/api/auth/login")) && (authHeader == null || authHeader.isBlank())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Authorization header/Token is missing or empty\"}");
        } else {
            // Handles unauthorized access with a 401 response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"" + authException.getMessage() + "\"}");
        }
    }
}
