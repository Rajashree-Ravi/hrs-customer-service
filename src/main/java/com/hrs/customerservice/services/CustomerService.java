package com.hrs.customerservice.services;

import java.util.List;

import com.hrs.customerservice.models.CustomerDto;

public interface CustomerService {

	List<CustomerDto> getAllCustomers();

	CustomerDto getCustomerById(long id);

	CustomerDto getCustomerByUsername(String username);

	CustomerDto createCustomer(CustomerDto customer);

	CustomerDto updateCustomer(long id, CustomerDto customer);

	void deleteCustomer(long id);

	void createTestCustomers();

}
