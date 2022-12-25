package com.customer.system.customersystem.service;

import com.customer.system.customersystem.dao.CustomerDao;
import com.customer.system.customersystem.domain.AuthModel;
import com.customer.system.customersystem.domain.Customer;
import com.customer.system.customersystem.model.CustomerDTO;
import com.customer.system.customersystem.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	private JwtUtils jwtUtils;

	private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);


	@Override
	public Customer findById(long id) {
		return customerDao.findById(id);
	}

	@Override
	public Customer updateCustomer(CustomerDTO customerDTO) {
		return customerDao.save(customerDao.findByUsername(customerDTO.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Customer does not exist")));
	}

	@Override
	public void deleteCustomerByEmail(String email) {

		Optional<Customer> existingCustomer = customerDao.findByEmail(email);

		if (existingCustomer.isPresent()) {
			Customer customer = existingCustomer.get();
			customerDao.deleteById(customer.getId());
		} else {
			throw new IllegalStateException("Email does not exist");
		}
	}

	@Override
	public String createCustomer(CustomerDTO customer) {

		validateCustomer(customer);

		boolean customerExist = customerDao.findByUsername(customer.getUsername()).isPresent();
		if(customerExist) {
			throw new IllegalStateException("Username already taken");
		}
		String password = customer.getPassword();
		AuthModel auth = new AuthModel(customer.getUsername(), password);
		return getToken(auth);
	}

	private String getToken(AuthModel authModel) {
		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getUsername(), authModel.getPassword()));
			if(auth.isAuthenticated()) {
				return  "{\"token\":\"" +
						jwtUtils.generateJwtToken(authModel.getUsername()) +
						"\"}";
			} else {
				return "Auth not approved";
			}
		} catch (Exception e){
			LOG.error(" getToken token create error username: {}", authModel.getUsername());
			throw new IllegalStateException("An error occurred while creating token");
		}
	}

	private void validateCustomer(CustomerDTO customer) {
		if(!StringUtils.hasLength(customer.getUsername()) || !StringUtils.hasLength(customer.getPassword())
				|| StringUtils.containsWhitespace(customer.getUsername()) || StringUtils.containsWhitespace(customer.getPassword())) {

			throw new RuntimeException("Username or password is not valid");
		}
	}
}
