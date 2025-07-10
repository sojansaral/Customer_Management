package com.cts.service;

import com.cts.dto.LoginDto;

//Service interface for authentication operations 
public interface AuthService {
    
    // Authenticates a user and returns a JWT token 
    String login(LoginDto loginDto);
}
