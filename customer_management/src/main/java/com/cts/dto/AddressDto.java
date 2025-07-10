package com.cts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {
	@NotBlank(message="houseNo should not be blank")
	@Pattern(regexp = "[0-9]+",message="houseNo should contain numbers only")
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
	@Pattern(regexp = "[0-9]+",message="pin should contain numbers only")
	private String pin;
}
