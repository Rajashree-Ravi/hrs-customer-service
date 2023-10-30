package com.hrs.customerservice.entities;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerAuthentication extends Customer implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private Collection<? extends GrantedAuthority> authorities;

	@JsonIgnore
	private String password;

	public CustomerAuthentication(Customer customer) {
		super(customer.getUsername(), customer.getPassword(), customer.getGrantedAuthoritiesList(), customer.getName(),
				customer.getEmail());
		this.username = customer.getUsername();
		this.password = customer.getPassword();
		this.authorities = customer.getGrantedAuthoritiesList();
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CustomerAuthentication customerAuthentication = (CustomerAuthentication) o;

		return Objects.equals(username, customerAuthentication.username);
	}

}
