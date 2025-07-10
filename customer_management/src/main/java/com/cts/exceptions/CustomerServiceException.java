package com.cts.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// Custom exception for handling service-level errors 
public class CustomerServiceException extends RuntimeException {
    
    // HTTP status code representing the error type 
    private HttpStatus status;
    
    // Error message describing the issue 
    private String message;

    
    
    
}
