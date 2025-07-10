package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for authentication response containing JWT token 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    
    // The JWT token issued upon successful authentication 
    private String jwtToken;
    
    // The token type, defaulting to "Bearer" 
    private String type = "Bearer";
}
