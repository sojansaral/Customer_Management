package com.cts.entities;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name="customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message="FirstName should not be blank")
	@Column(nullable = false)
	private String firstName;
	
	@NotBlank(message="LastName should not be blank")
	private String lastName;
    
    
	private String nickName;
    
	@NotBlank(message="Gender should not be blank")
	private String gender;
	
	@NotNull(message="Age should not be blank")
	private int age;
	
	@NotBlank(message="Qualification should not be blank")
	private String qualification;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="houseNo",column=@Column(name="permanent_houseNo")),
		@AttributeOverride(name="street",column=@Column(name="permanent_street")),
		@AttributeOverride(name="landmark",column=@Column(name="permanent_landmark")),
		@AttributeOverride(name="city",column=@Column(name="permanent_city")),
		@AttributeOverride(name="state",column=@Column(name="permanent_state")),
		@AttributeOverride(name="pin",column=@Column(name="permanent_pin"))
	})
	@Valid
	private Address permanent_address;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="houseNo",column=@Column(name="communication_houseNo")),
		@AttributeOverride(name="street",column=@Column(name="communication_street")),
		@AttributeOverride(name="landmark",column=@Column(name="communication_landmark")),
		@AttributeOverride(name="city",column=@Column(name="communication_city")),
		@AttributeOverride(name="state",column=@Column(name="communication_state")),
		@AttributeOverride(name="pin",column=@Column(name="communication_pin"))
	})
	@Valid
	private Address communication_address;
	
	@NotBlank(message="Notes should not be blank")
	private String notes;
	
}
