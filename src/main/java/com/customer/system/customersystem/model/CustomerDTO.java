package com.customer.system.customersystem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomerDTO {

	private Long id;

	private String email;
	private String username;
	private String password;
}
