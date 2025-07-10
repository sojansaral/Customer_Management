package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for user login credentials 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
    
    // Username or email used for authentication 
    private String usernameOrEmail;
    
    // Password for authentication 
    private String password;
}
