package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for standard error response 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    
    // Error message describing the issue 
    private String message;
    
    //HTTP status code associated with the error 
    private int status;
}
