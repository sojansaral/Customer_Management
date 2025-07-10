package com.cts.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Utility class for generating hashed passwords using BCrypt 
public class PasswordGenerator {

    // Main method to generate and print hashed passwords 
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Encoding passwords using BCrypt
        System.out.println(encoder.encode("soja@12"));
        System.out.println(encoder.encode("sara@12"));
    }
}
