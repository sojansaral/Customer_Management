package com.cts.dto;

import com.cts.entities.Address;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO for updating customer details with optional fields 
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerUpdate {

    //First name of the customer, allowing only letters 
    @Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters")
    private String firstName;

    // Last name of the customer, allowing only letters 
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    private String lastName;

    // Gender of the customer, must be 'M' or 'F' 
    @Pattern(regexp = "[MmFf]", message = "Gender should be either M or F")
    private String gender;

    // Age of the customer, must be between 18 and 60 
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age must not exceed 60")
    private Integer age;

    // Nickname of the customer, allowing only letters 
    @Pattern(regexp = "[a-zA-Z]+", message = "Nick name should contain only letters")
    private String nickName;

    // Qualification of the customer 
    @Pattern(regexp = "[a-zA-Z]+[0-9]*")
    private String qualification;
    
    // Permanent address of the customer 
    @Embedded
    @Valid
    private Address permanent_address;
    
    // Communication address of the customer 
    @Embedded
    @Valid
    private Address communication_address;
    
    // Additional notes related to the customer 
    private String notes;
}
