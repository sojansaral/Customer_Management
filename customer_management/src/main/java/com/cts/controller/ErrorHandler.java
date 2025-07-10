package com.cts.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import com.cts.dto.ErrorResponse;
import com.cts.exceptions.CustomerNotFoundException;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// Global exception handler for application-wide error handling 
@ControllerAdvice
public class ErrorHandler {

    // Handles CustomerNotFoundException and returns a NOT_FOUND response 
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex){
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    // Handles validation errors and returns a BAD_REQUEST response 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex){
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append(" ");
        }
        ErrorResponse response = new ErrorResponse();
        response.setMessage(sb.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // Handles type mismatch errors and returns a BAD_REQUEST response 
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid data type: " + ex.getName() + " should be a number");
        return ResponseEntity.badRequest().body(error);
    }
    
    // Handles unsupported HTTP methods and returns a METHOD_NOT_ALLOWED response 
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMethod(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    } 
    
    // Handles SQL integrity constraint violations and returns a BAD_REQUEST response 
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSqlExceptions(SQLIntegrityConstraintViolationException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
