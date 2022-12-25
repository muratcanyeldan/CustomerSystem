package com.customer.system.customersystem.controller;

import com.customer.system.customersystem.mapper.CustomerMapper;
import com.customer.system.customersystem.model.CustomerDTO;
import com.customer.system.customersystem.service.CustomerService;
import com.customer.system.customersystem.utils.LoggerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/customer")
@Api(value = "Customer Controller Swagger documentation")
public class CustomerController {

	private static final Logger LOG = LoggerUtils.getLogger();

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerMapper customerMapper;

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Find customer by id")
	public CustomerDTO findById(@PathVariable Long id) {
		return customerMapper.customer2DTO(customerService.findById(id));
	}

	@PutMapping(value = "/update")
	@ApiOperation(value = "Update customer")
	public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerMapper.customer2DTO(customerService.updateCustomer(customerDTO));
	}

	@DeleteMapping("{email}")
	@ApiOperation(value = "Delete customer by email")
	public void deleteCustomer(@PathVariable String email) {
		customerService.deleteCustomerByEmail(email);
	}

	@PostMapping("/create")
	@ApiOperation(value = "Create new customer")
	public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customer) {
		String token = customerService.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}
}
