package com.hrs.customerservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrs.customerservice.entities.Customer;
import com.hrs.customerservice.repositories.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/welcome")
	private ResponseEntity<String> displayWelcomeMessage() {
		return new ResponseEntity<>("Welcome to customer service !!", HttpStatus.OK);
	}

	@PostMapping("/test")
	private ResponseEntity<String> save() {

		GrantedAuthority grantedAuthorityAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
		List<GrantedAuthority> grantedAuthorityListAdmin = new ArrayList<>();
		grantedAuthorityListAdmin.add(grantedAuthorityAdmin);

		Customer admin = new Customer("admin", "$2a$12$0d6AcE1NETW7lpc4hBXYu.o17T00scSL1KVVRh07LGd2Ai8JxzNx2",
				grantedAuthorityListAdmin, "Admin", "hrs.admin@gmail.com");
		customerRepository.save(admin);

		GrantedAuthority grantedAuthorityUser = new SimpleGrantedAuthority("ROLE_USER");
		List<GrantedAuthority> grantedAuthorityListUser = new ArrayList<>();
		grantedAuthorityListUser.add(grantedAuthorityUser);

		Customer user = new Customer("user", "$2a$12$GPDdAx474QC.0DpwyVoG2.nH9kwb9.H6.XUIrqznfPo2.zYJhcEzS",
				grantedAuthorityListUser, "User", "hrs.user@gmail.com");
		customerRepository.save(user);

		return new ResponseEntity<>("Customers Created !!", HttpStatus.OK);
	}

}
