package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.CustomerDto;
import com.cts.dto.CustomerUpdate;
import com.cts.entities.Customer;
import com.cts.service.CustomerService;

import jakarta.validation.Valid;
import lombok.experimental.PackagePrivate;

//Controller for managing customer-related operations 
@RestController
@RequestMapping("/cms")
public class CustomerController {
    
    private CustomerService customerService;

    // Constructor to inject CustomerService dependency 
    @Autowired
    public CustomerController(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }
    
    // Retrieves all customers
    @GetMapping()
    public List<CustomerDto> getAll(){
        return customerService.getAll();
    }
    
    // Retrieves a customer by ID 
    @GetMapping("/read/{id}")
    public CustomerDto getById(@PathVariable int id) {
        return customerService.getById(id);
    }
    
    // Adds a new customer (requires ADMIN role) 
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        var customers = customerService.addCustomer(customerDto);
        return new ResponseEntity<CustomerDto>(customers, HttpStatus.CREATED);
    }
    
    // Updates a customer using PUT (full replacement) 
    @PutMapping("/put/{id}")
    public ResponseEntity<CustomerDto> updatecustomerUsingPut(@PathVariable int id, @Valid @RequestBody CustomerDto customerDto) {
    	CustomerDto cust=customerService.updateCustomer(id, customerDto);
        return new ResponseEntity<CustomerDto>(cust,HttpStatus.CREATED);
    }
    
    // Partially updates a customer using PATCH 
    @PatchMapping("/save/{id}")
    public CustomerDto updateCustomer(@PathVariable int id, @Valid @RequestBody CustomerUpdate customer) {
        return customerService.updatePartialCustomer(id, customer);
    }
    
    // Deletes a customer by ID (requires ADMIN role) 
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id){
        customerService.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }
}
