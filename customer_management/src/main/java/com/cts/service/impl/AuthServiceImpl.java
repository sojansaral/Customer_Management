package com.cts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cts.dto.LoginDto;
import com.cts.security.JwtTokenProvider;
import com.cts.service.AuthService;

// Service implementation for handling authentication operations 
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // Authenticates a user and returns a JWT token 
    @Override
    public String login(LoginDto loginDto) {
        
        // Create authentication token using username/email and password
        UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Generate and return JWT token
        return jwtTokenProvider.generateToken(authentication);
    }
}
