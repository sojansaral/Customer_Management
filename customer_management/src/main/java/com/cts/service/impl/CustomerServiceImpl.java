package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.CustomerDto;
import com.cts.dto.CustomerUpdate;
import com.cts.dto.AddressDto;
import com.cts.entities.Address;
import com.cts.entities.Customer;
import com.cts.exceptions.CustomerNotFoundException;
import com.cts.repository.CustomerRepository;
import com.cts.service.CustomerService;

// Implementation of CustomerService handling customer operations 
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    // Constructor to inject CustomerRepository dependency 
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    // Retrieves all customers 
    @Override
    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream().map(this::mapToDto).toList();
    }

    // Retrieves a customer by ID 
    @Override
    public CustomerDto getById(int id) {
    	Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        return mapToDto(customer);
    }

    // Adds a new customer 
    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
    	Customer customer = mapToEntity(customerDto);
        Customer saved = customerRepository.save(customer);
        return mapToDto(saved);
    }

    // Updates specific customer fields (partial update) 
    @Override
    public CustomerDto updatePartialCustomer(int id, CustomerUpdate customer) {
    	CustomerUpdate customerUpdate=new CustomerUpdate();
    	if(customerUpdate.equals(customer)) {
    		throw new CustomerNotFoundException("Please enter values to update");
    	}
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with Id " + id + " is not found");
        }
        Customer cust = customerRepository.findById(id).get();
        
        // Update non-null fields
        if (customer.getAge() != null) 
        	cust.setAge(customer.getAge());
        if (customer.getFirstName() != null) 
        	cust.setFirstName(customer.getFirstName());
        if (customer.getLastName() != null) 
        	cust.setLastName(customer.getLastName());
        if (customer.getGender() != null) 
        	cust.setGender(customer.getGender());
        if (customer.getNickName() != null) 
        	cust.setNickName(customer.getNickName());
        if (customer.getQualification() != null) 
        	cust.setQualification(customer.getQualification());
        if (customer.getNotes() != null) 
        	cust.setNotes(customer.getNotes());

        // Update addresses if present
        if (customer.getPermanent_address() != null) {
            if (customer.getPermanent_address().getHouseNo() != null) 
            	cust.getPermanent_address().setHouseNo(customer.getPermanent_address().getHouseNo());
            if (customer.getPermanent_address().getStreet() != null) 
            	cust.getPermanent_address().setStreet(customer.getPermanent_address().getStreet());
            if (customer.getPermanent_address().getLandmark() != null) 
            	cust.getPermanent_address().setLandmark(customer.getPermanent_address().getLandmark());
            if (customer.getPermanent_address().getCity() != null) 
            	cust.getPermanent_address().setCity(customer.getPermanent_address().getCity());
            if (customer.getPermanent_address().getState() != null) 
            	cust.getPermanent_address().setState(customer.getPermanent_address().getState());
            if (customer.getPermanent_address().getPin() != null) 
            	cust.getPermanent_address().setPin(customer.getPermanent_address().getPin());
        }

        if (customer.getCommunication_address() != null) {
            if (customer.getCommunication_address().getHouseNo() != null) 
            	cust.getCommunication_address().setHouseNo(customer.getCommunication_address().getHouseNo());
            if (customer.getCommunication_address().getStreet() != null) 
            	cust.getCommunication_address().setStreet(customer.getCommunication_address().getStreet());
            if (customer.getCommunication_address().getLandmark() != null) 
            	cust.getCommunication_address().setLandmark(customer.getCommunication_address().getLandmark());
            if (customer.getCommunication_address().getCity() != null) 
            	cust.getCommunication_address().setCity(customer.getCommunication_address().getCity());
            if (customer.getCommunication_address().getState() != null) 
            	cust.getCommunication_address().setState(customer.getCommunication_address().getState());
            if (customer.getCommunication_address().getPin() != null) 
            	cust.getCommunication_address().setPin(customer.getCommunication_address().getPin());
        }

        Customer updatedCustomer = customerRepository.save(cust);
        return mapToDto(updatedCustomer);
    }

    // Deletes a customer by ID 
    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    // Updates a customer's complete details 
    @Override
    public CustomerDto updateCustomer(int id, CustomerDto customerDto) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with Id " + id + " is not found");
        }
        Customer cust = customerRepository.findById(id).get();

        // Full update of customer fields
        cust.setFirstName(customerDto.getFirstName());
        cust.setLastName(customerDto.getLastName());
        cust.setNickName(customerDto.getNickName());
        cust.setGender(customerDto.getGender());
        cust.setAge(customerDto.getAge());
        cust.setPermanent_address(mapToEntityAddress(customerDto.getPermanent_address()));
        cust.setCommunication_address(mapToEntityAddress(customerDto.getCommunication_address()));
        cust.setQualification(customerDto.getQualification());
        cust.setNotes(customerDto.getNotes());
        
        Customer updatedCustomer = customerRepository.save(cust);
        return mapToDto(updatedCustomer);
    }
    
    //Maps a Customer entity to a CustomerDto
    private CustomerDto mapToDto(Customer customer) {
		CustomerDto dto = new CustomerDto();

		dto.setId(customer.getId());
		dto.setFirstName(customer.getFirstName());
		dto.setLastName(customer.getLastName());
		dto.setNickName(customer.getNickName());
		dto.setAge(customer.getAge());
		dto.setGender(customer.getGender());
		dto.setQualification(customer.getQualification());
		dto.setNotes(customer.getNotes());
		
		// Map Address to AddressDto
		if (customer.getPermanent_address() != null) {
			dto.setPermanent_address(mapToDtoAddress(customer.getPermanent_address()));
		} else {
			dto.setPermanent_address(new AddressDto()); // Set an empty AddressDto if null
		}

		// Map Trades to TradeDto
		if (customer.getCommunication_address() != null) {
			dto.setCommunication_address(mapToDtoAddress(customer.getCommunication_address()));
		} else {
			dto.setCommunication_address(new AddressDto()); // Set an empty AddressDto if null
		}

		return dto;
	}
    
    //Converts an Address entity to an AddressDto
    private AddressDto mapToDtoAddress(Address address) {
    	AddressDto addressDto=new AddressDto();
    	addressDto.setHouseNo(address.getHouseNo());
    	addressDto.setLandmark(address.getLandmark());
    	addressDto.setCity(address.getCity());
    	addressDto.setStreet(address.getStreet());
    	addressDto.setState(address.getState());
    	addressDto.setPin(address.getPin());
    	
    	return addressDto;
    }
    
    
    //Maps a CustomerDto to a Customer entity
    private Customer mapToEntity(CustomerDto dto) {
	    Customer customer = new Customer();

	    customer.setId(dto.getId());
		customer.setFirstName(dto.getFirstName());
		customer.setLastName(dto.getLastName());
		customer.setNickName(dto.getNickName());
		customer.setAge(dto.getAge());
		customer.setGender(dto.getGender());
		customer.setQualification(dto.getQualification());
		customer.setNotes(dto.getNotes());

		// Map permanent address
		if (dto.getPermanent_address() != null) {
			customer.setPermanent_address(mapToEntityAddress(dto.getPermanent_address()));
		}

		// Map communication address
		if (dto.getCommunication_address() != null) {
			customer.setCommunication_address(mapToEntityAddress(dto.getCommunication_address()));
		} 
		
	    return customer;
	}
    
    //Converts an AddressDto to an Address entity
    private Address mapToEntityAddress(AddressDto addressDto) {
    	Address address=new Address();
    	address.setHouseNo(addressDto.getHouseNo());
    	address.setLandmark(addressDto.getLandmark());
    	address.setCity(addressDto.getCity());
    	address.setStreet(addressDto.getStreet());
    	address.setState(addressDto.getState());
    	address.setPin(addressDto.getPin());
    	
    	return address;
    }
    
    
}
