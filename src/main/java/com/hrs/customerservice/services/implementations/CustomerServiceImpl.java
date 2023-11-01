package com.hrs.customerservice.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.hrs.customerservice.entities.Customer;
import com.hrs.customerservice.exceptions.CustomerNotFoundException;
import com.hrs.customerservice.models.CustomerDto;
import com.hrs.customerservice.repositories.CustomerRepository;
import com.hrs.customerservice.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Retrieves all the existing customers from the customerdb
	 */
	@Override
	public List<CustomerDto> getAllCustomers() {
		List<CustomerDto> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customer -> {
			customers.add(mapper.map(customer, CustomerDto.class));
		});
		return customers;
	}

	/**
	 * Retrieves a customer with unique customer id
	 */
	@Override
	public CustomerDto getCustomerById(long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent())
			return mapper.map(customer.get(), CustomerDto.class);
		else
			throw new CustomerNotFoundException("Customer with id: " + id + " not found");
	}

	/**
	 * Retrieves a customer with unique username
	 */
	@Override
	public CustomerDto getCustomerByUsername(String username) {
		Optional<Customer> customer = customerRepository.findByUsername(username);
		if (customer.isPresent())
			return mapper.map(customer.get(), CustomerDto.class);
		else
			throw new CustomerNotFoundException("Customer with username: " + username + " not found");
	}

	/**
	 * Registers a new customer
	 */
	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer customer = mapper.map(customerDto, Customer.class);
		return mapper.map(customerRepository.save(customer), CustomerDto.class);
	}

	/**
	 * Updates a customer with unique customer id
	 */
	@Override
	public CustomerDto updateCustomer(long id, CustomerDto customerDto) {
		Optional<Customer> customerFound = customerRepository.findById(id);

		if (customerFound.isPresent()) {
			Optional<Customer> updatedCustomer = customerFound.map(existingCustomer -> {
				Customer customer = mapper.map(customerDto, Customer.class);
				return customerRepository.save(existingCustomer.updateWith(customer));
			});

			return mapper.map(updatedCustomer.get(), CustomerDto.class);
		} else
			throw new CustomerNotFoundException("Customer with id: " + id + " not found");
	}

	/**
	 * Deletes a customer with unique customer id
	 */
	@Override
	public void deleteCustomer(long id) {
		if (getCustomerById(id) != null) {
			customerRepository.deleteById(id);
			log.info("Customer deleted Successfully");
		} else {
			throw new CustomerNotFoundException("Customer with id: " + id + " not found");
		}
	}

	/**
	 * Creates a admin and a test user in the customerdb
	 */
	@Override
	public void createTestCustomers() {

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
	}

}
