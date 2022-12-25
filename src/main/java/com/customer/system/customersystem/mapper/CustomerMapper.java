package com.customer.system.customersystem.mapper;

import com.customer.system.customersystem.domain.Customer;
import com.customer.system.customersystem.model.CustomerDTO;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

	public CustomerDTO customer2DTO(Customer customer) {

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setUsername(customer.getUsername());
		customerDTO.setPassword(customer.getPassword());
		customerDTO.setId(customer.getId());
		return customerDTO;
	}
}
