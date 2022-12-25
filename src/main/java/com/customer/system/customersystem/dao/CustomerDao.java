package com.customer.system.customersystem.dao;

import com.customer.system.customersystem.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

	Customer findById(long id);

	Optional<Customer> findByUsername(String username);

	Optional<Customer> findByEmail(String email);

}
