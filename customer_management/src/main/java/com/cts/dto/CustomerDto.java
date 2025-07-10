package com.cts.dto;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
	private int id;
	
	@NotBlank(message="FirstName should not be blank")
	@Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters")
	private String firstName;
	
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
	@NotNull(message="LastName should not be blank")
	private String lastName;
    
    @Pattern(regexp = "[a-zA-Z]+", message = "Nick name should contain only letters")
	private String nickName;
    
	@NotNull(message="Gender should not be blank")
	@Pattern(regexp = "[MmFf]", message="Gender should be either M or F")
	@Size(min = 1, max = 1, message="Gender should be a single character")
	private String gender;
	
	@NotNull(message="Age should not be blank")
	@Max(60)
	@Min(18)
	private int age;
	
	@NotBlank(message="Qualification should not be blank")
    @Pattern(regexp = "[a-zA-Z\s']+[0-9]*")
	private String qualification;
	
	@Valid
	private AddressDto permanent_address;
	
	@Valid
	private AddressDto communication_address;
	
	@NotBlank(message="Notes should not be blank")
	private String notes;
}
