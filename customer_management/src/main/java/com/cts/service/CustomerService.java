package com.cts.service;

import java.util.List;

import com.cts.dto.CustomerDto;
import com.cts.dto.CustomerUpdate;

// Service interface for customer management operations 
public interface CustomerService {
    
    // Retrieves all customers 
    List<CustomerDto> getAll();

    // Retrieves a customer by ID 
    CustomerDto getById(int id);

    // Adds a new customer 
    CustomerDto addCustomer(CustomerDto customerDto);

    // Updates a customer's complete details 
    CustomerDto updateCustomer(int id, CustomerDto customerDto);

    // Partially updates a customer's details 
    CustomerDto updatePartialCustomer(int id, CustomerUpdate customer);

    // Deletes a customer by ID 
    void deleteCustomer(int id);
}
