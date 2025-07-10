package com.cts.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.dto.AddressDto;
import com.cts.dto.CustomerDto;
import com.cts.dto.CustomerUpdate;
import com.cts.entities.Address;
import com.cts.entities.Customer;
import com.cts.exceptions.CustomerNotFoundException;
import com.cts.repository.CustomerRepository;

//Unit tests for CustomerServiceImpl class
@SpringBootTest
class CustomerServiceImplTest {
	
	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	CustomerServiceImpl customerService;
	
	Customer cust1;
	Address permenant1;
	Address communication1;
	Customer cust2;
	Address permenant2;
	Address communication2;
	CustomerUpdate c1;
	List<Customer> customers;
	
	AddressDto permanentDto1;
	AddressDto communicationDto1;
	CustomerDto custDto1;
	
	 //Initializes test data before each test
	@BeforeEach
	public void init() {
		permenant1=new Address("567", "MG Road", "Near City Library", "Bangalore", "KA", "560001");
		communication1=new Address("890", "Indiranagar 2nd Stage", "Opposite Metro Station"," Bangalore"," KA", "560038");
		cust1=new Customer(1,"antony","raj","raju","M",53,"bsc",permenant1,communication1,"prefers weekend calls");
		permenant2=new Address("102", "Greenway Street"," Near Central Mall"," Chennai"," TN"," 600042");
		communication2=new Address("22", "Maple Residency", "Next to City Park"," Hyderabad"," TS"," 500081");
		cust2=new Customer(2,"joycee","A","joy","F",46,"bsc",permenant2,communication2,"available to take calls on weekdays");
		c1=new CustomerUpdate("antony","raj","M",45,"raju","BSC",permenant1,communication1,"available");
		customers=new ArrayList<>();
		customers.add(cust1);
		customers.add(cust2);
		
		
		permanentDto1=new AddressDto("567", "MG Road", "Near City Library", "Bangalore", "KA", "560001");
		communicationDto1=new AddressDto("890", "Indiranagar 2nd Stage", "Opposite Metro Station"," Bangalore"," KA", "560038");
		custDto1=new CustomerDto(1,"antony","raj12345","raju","M",53,"bsc",permanentDto1,communicationDto1,"prefers weekend calls");
		
	}

	//Tests fetching all customers
	@Test
	void testGetAll() {
		when(customerRepository.findAll()).thenReturn(customers);
		
		List<CustomerDto> result=customerService.getAll();
		
		assertEquals(2, result.size());
		assertEquals("antony", result.get(0).getFirstName());
		verify(customerRepository,times(1)).findAll();
	}

	//Tests fetching a customer by ID
	@Test
	void testGetById() {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(cust1));
		
		CustomerDto customer=customerService.getById(1);
		
		assertNotNull(customer);
		assertEquals("raju",customer.getNickName());
		verify(customerRepository,times(1)).findById(1);
	}

	//Tests adding a new customer
	@Test
	void testAddCustomer() {
		when(customerRepository.save(any(Customer.class))).thenReturn(cust1);
		
		CustomerDto customer=customerService.addCustomer(custDto1);
		assertNotNull(customer);
		verify(customerRepository,times(1)).save(cust1);
	}

	@Test
	void testUpdateCustomer() {
		when(customerRepository.existsById(anyInt())).thenReturn(true);
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(cust1));
		when(customerRepository.save(any(Customer.class))).thenReturn(cust2);
		
		CustomerDto customer=customerService.updateCustomer(1,custDto1);
		
		assertEquals("joycee", customer.getFirstName());
		verify(customerRepository,times(1)).existsById(1);
		verify(customerRepository,times(1)).findById(1);
		verify(customerRepository,times(1)).save(cust1);
		
	}
	
	//Tests deleting a customer that does not exist 
	@Test
	void testUpdateCustomerNotFoundException() {
		when(customerRepository.existsById(anyInt())).thenReturn(false);
		
		assertThrows(CustomerNotFoundException.class, ()->customerService.updateCustomer(1, custDto1));
		verify(customerRepository,never()).save(cust1);
		verify(customerRepository,times(1)).existsById(1);
	}
	
	
	//Tests partially updating a customer 
	@Test
	void testPartialUpdateCustomer() {
		when(customerRepository.existsById(anyInt())).thenReturn(true);
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(cust2));
		when(customerRepository.save(any(Customer.class))).thenReturn(cust1);
		
		CustomerDto customer=customerService.updatePartialCustomer(2,c1);
		
		assertEquals("antony", customer.getFirstName());
		verify(customerRepository,times(1)).existsById(2);
		verify(customerRepository,times(1)).findById(2);
		verify(customerRepository,times(1)).save(cust2);
	}

	//Tests deleting a customer
	@Test
	void testDeleteCustomer() {
		when(customerRepository.existsById(anyInt())).thenReturn(true);
		
		customerService.deleteCustomer(1);
		
		verify(customerRepository,times(1)).deleteById(1);
	}
	
	//Tests deleting a customer that does not exist
	@Test
	void testDeleteCustomerNotFoundException() {
		when(customerRepository.existsById(anyInt())).thenReturn(false);
		
		verify(customerRepository,never()).deleteById(1);
	}
}
