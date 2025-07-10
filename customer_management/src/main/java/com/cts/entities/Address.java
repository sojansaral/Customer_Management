package com.cts.entities;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Address {
	@NotBlank(message="houseNo should not be blank")
	private String houseNo;
	
	@NotBlank(message="Street should not be blank")
	private String street;
	
	@NotBlank(message="Landmark should not be blank")
	private String landmark;
	
	@NotBlank(message="City should not be blank")
	private String city;
	
	@NotBlank(message="State should not be blank")
	private String state;
	
	@NotBlank(message="Pin should not be blank")
	private String pin;
}
