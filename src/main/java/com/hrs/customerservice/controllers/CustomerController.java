package com.hrs.customerservice.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrs.customerservice.messaging.TopicProducer;
import com.hrs.customerservice.models.CustomerDto;
import com.hrs.customerservice.services.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api(produces = "application/json", value = "Operations pertaining to manage customers in hotel reservation system")
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	private TopicProducer topicProducer;
	
	public CustomerController(TopicProducer producer) {
		this.topicProducer = producer;
	}

	@GetMapping("/welcome")
	private ResponseEntity<String> displayWelcomeMessage() {
		return new ResponseEntity<>("Welcome to customer service !!", HttpStatus.OK);
	}

	@GetMapping("/retrieve/{id}")
	@ApiOperation(value = "Retrieve the customer with the specified customer id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved customer"),
			@ApiResponse(code = 404, message = "Customer with specified customer id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") @Valid long id) {
		CustomerDto customer = customerService.getCustomerById(id);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PostMapping("/register")
	@ApiOperation(value = "Register a new customer", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully registered the customer"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<CustomerDto> registerCustomer(@RequestBody @Valid CustomerDto customer) {

		CustomerDto savedCustomer = customerService.createCustomer(customer);

		log.info("Customer registered successfully hence publishing the registration event.");
		topicProducer.send(savedCustomer);
		
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	@ApiOperation(value = "Update a customer information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated customer information"),
			@ApiResponse(code = 404, message = "Customer with specified customer id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") @Valid long id,
			@RequestBody @Valid CustomerDto customer) {
		return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "Delete a customer", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted customer information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteCustomer(@PathVariable("id") @Valid long id) {
		customerService.deleteCustomer(id);
		return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
	}

	@PostMapping("/test")
	private ResponseEntity<String> createTestCustomers() {
		customerService.createTestCustomers();
		return new ResponseEntity<>("Customers Created !!", HttpStatus.OK);
	}

}
