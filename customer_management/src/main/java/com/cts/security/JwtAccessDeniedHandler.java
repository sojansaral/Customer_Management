package com.cts.security;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
 
import java.io.IOException;

// Custom handler for handling access-denied exceptions in JWT-based authentication 
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    // Handles AccessDeniedException by returning a 403 Forbidden response 
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        System.out.println("Handling AccessDeniedException");
        
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Access denied\"}");
        response.getWriter().flush();
    }
}
