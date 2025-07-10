package com.cts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.dto.CustomerUpdate;
import com.cts.dto.AddressDto;
import com.cts.dto.CustomerDto;
import com.cts.entities.Address;
import com.cts.entities.Customer;
import com.cts.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

//Unit tests for CustomerController class
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	CustomerService customerService;
	
	@Autowired
	ObjectMapper mapper;
	
	Customer cust1;
	Address permenant1;
	Address communication1;
	Customer cust2;
	Address permenant2;
	Address communication2;
	CustomerUpdate c1;
	CustomerUpdate c2;
	List<Customer> customers;
	List<CustomerDto> custDtoList;
	
	
	AddressDto permanentDto1;
	AddressDto communicationDto1;
	AddressDto permanentDto2;
	AddressDto communicationDto2;
	CustomerDto custDto1;
	CustomerDto custDto2;
	
	//Initializes test data before each test
	@BeforeEach
	public void init() {
		permanentDto1=new AddressDto("567", "MG Road", "Near City Library", "Bangalore", "KA", "560001");
		communicationDto1=new AddressDto("890", "Indiranagar 2nd Stage", "Opposite Metro Station"," Bangalore"," KA", "560038");
		custDto1=new CustomerDto(1,"antony","raj","raju","M",53,"bsc",permanentDto1,communicationDto1,"prefers weekend calls");
		
		permanentDto2=new AddressDto("102", "Greenway Street"," Near Central Mall"," Chennai"," TN"," 600042");
		communicationDto2=new AddressDto("22", "Maple Residency", "Next to City Park"," Hyderabad"," TS"," 500081");
		custDto2=new CustomerDto(2,"joycee","A","joy","F",46,"bsc",permanentDto2,communicationDto2,"available to take calls on weekdays");
		
		custDtoList=new ArrayList<CustomerDto>();
		custDtoList.add(custDto1);
		custDtoList.add(custDto2);		
	}
	
	//Tests fetching all customers
	@Test
	@WithMockUser
	void testGetAll() throws Exception {
		when(customerService.getAll()).thenReturn(custDtoList);
		
		mockMvc.perform(get("/cms"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(2))
		.andExpect(jsonPath("$[0].firstName").value("antony"));
		
		verify(customerService,times(1)).getAll();
	}

	//Tests fetching a customer by ID
	@Test
	@WithMockUser
	void testGetById() throws Exception {
		when(customerService.getById(anyInt())).thenReturn(custDto2);
		
		mockMvc.perform(get("/cms/read/2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName").value("joycee"));
		
		verify(customerService,times(1)).getById(2);
	}

	//Tests adding a new customer 
	@Test
	@WithMockUser(roles="ADMIN")
	void testAddCustomer() throws Exception {
		when(customerService.addCustomer(any(CustomerDto.class))).thenReturn(custDto1);
		
		var jsonCustomer=mapper.writeValueAsString(custDto1);
		
		mockMvc.perform(post("/cms/save").content(jsonCustomer)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.age").value(53));
	}

	// Tests partial update of a customer
	@Test
	@WithMockUser
	void testPartialUpdateCustomer() throws Exception {
		when(customerService.updatePartialCustomer(anyInt(), any(CustomerUpdate.class))).thenReturn(custDto1);
		
		var jsonCustomer=mapper.writeValueAsString(custDto1);
		mockMvc.perform(patch("/cms/save/1")
				.content(jsonCustomer)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName").value("antony"));
	}
	
	//Tests updating a customer 
	@Test
	@WithMockUser
	void testUpdateCustomer() throws Exception {
		when(customerService.updateCustomer(anyInt(), any(CustomerDto.class))).thenReturn(custDto1);
		
		var jsonCustomer=mapper.writeValueAsString(custDto1);
		mockMvc.perform(put("/cms/put/1")
				.content(jsonCustomer)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstName").value("antony"));
	}

	// Tests deleting a customer
	@Test
	@WithMockUser(roles="ADMIN")
	void testDeleteCustomer() {
		doNothing().when(customerService).deleteCustomer(anyInt());
		
	}

}
