package com.hrs.customerservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hrs.customerservice.entities.Customer;
import com.hrs.customerservice.entities.CustomerAuthentication;
import com.hrs.customerservice.repositories.CustomerRepository;

@Service
public class CustomerAuthenticationService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);

		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			CustomerAuthentication customerAuthentication = new CustomerAuthentication(customer);

			return customerAuthentication;
		} else {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

	}

}
