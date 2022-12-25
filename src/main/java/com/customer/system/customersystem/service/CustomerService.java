package com.customer.system.customersystem.service;

import com.customer.system.customersystem.domain.Customer;
import com.customer.system.customersystem.model.CustomerDTO;

public interface CustomerService {

	Customer findById(long id);

	Customer updateCustomer(CustomerDTO customerDTO);

	void deleteCustomerByEmail(String email);

	String createCustomer(CustomerDTO customer);
}
