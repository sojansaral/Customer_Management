package com.cts.exceptions;

// Custom exception for handling cases when a customer is not found 
public class CustomerNotFoundException extends RuntimeException {

    // Default constructor for CustomerNotFoundException 
    public CustomerNotFoundException() {
        super();
    }

    // Constructor with a custom error message 
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
