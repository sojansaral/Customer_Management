package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.AuthResponse;
import com.cts.dto.LoginDto;
import com.cts.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Controller for handling authentication requests
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    //Handles login requests and returns a JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        var result = authService.login(loginDto);
        AuthResponse response = new AuthResponse();
        response.setJwtToken(result);
        return ResponseEntity.ok(response);
    }
}
