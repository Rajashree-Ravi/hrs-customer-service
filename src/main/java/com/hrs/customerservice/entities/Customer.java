package com.hrs.customerservice.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.JoinColumn;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message="Invalid username: Username may not be blank.")
	@Column(unique = true)
	private String username;

	@NotBlank(message="Invalid password: Password may not be blank.")
	private String password;

	@ElementCollection(targetClass = GrantedAuthority.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "CUSTOMER_GRANTED_AUTHORITIES_LIST", joinColumns = @JoinColumn(name = "CUSTOMER_ID"))
	private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

	@NotBlank(message="Invalid name: Name may not be blank.")
	private String name;

	@NotBlank(message="Invalid email: Email may not be blank.")
	@Email(message = "Invalid email: Enter valid email")
	private String email;

	private String country;

	public Customer(String username, String password, Collection<GrantedAuthority> grantedAuthoritiesList, String name,
			String email) {
		super();
		this.username = username;
		this.password = password;
		this.grantedAuthoritiesList = grantedAuthoritiesList;
		this.name = name;
		this.email = email;
	}

	public Customer updateWith(Customer customer) {
		return new Customer(this.id, customer.username, customer.password, customer.grantedAuthoritiesList,
				customer.name, customer.email, customer.country);
	}

}