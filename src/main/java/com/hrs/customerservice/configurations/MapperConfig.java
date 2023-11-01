package com.hrs.customerservice.configurations;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hrs.customerservice.entities.Customer;
import com.hrs.customerservice.models.CustomerDto;

@Configuration
public class MapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(customerMap);
		mapper.addMappings(customerDtoMap);
		return mapper;
	}

	/**
	 * Mapping for converting List<String> into List<GrantedAuthority> on the Customer. 
	 * To use: modelMapper.addMappings(customerMap);
	 * 
	 */
	PropertyMap<CustomerDto, Customer> customerMap = new PropertyMap<CustomerDto, Customer>() {
		protected void configure() {
			using(stringToGrantedAuthorityConverter).map(source.getRoles())
					.setGrantedAuthoritiesList(new ArrayList<>());
		}
	};

	/**
	 * Mapping for converting List<GrantedAuthority> into List<String> on the CustomerDto. 
	 * To use: modelMapper.addMappings(customerDtoMap);
	 * 
	 */
	PropertyMap<Customer, CustomerDto> customerDtoMap = new PropertyMap<Customer, CustomerDto>() {
		protected void configure() {
			using(grantedAuthorityToStringConverter).map(source.getGrantedAuthoritiesList())
					.setRoles(new ArrayList<>());
		}
	};

	/**
	 * Converter for converting a List<String> on the DTO into List<GrantedAuthority>
	 * 
	 */
	Converter<List<String>, List<GrantedAuthority>> stringToGrantedAuthorityConverter = new Converter<List<String>, List<GrantedAuthority>>() {
		public List<GrantedAuthority> convert(MappingContext<List<String>, List<GrantedAuthority>> context) {
			List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
			for (String role : context.getSource()) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
				grantedAuthorityList.add(grantedAuthority);
			}
			return grantedAuthorityList;
		}
	};

	/**
	 * Converter for converting a List<GrantedAuthority> into List<String> on the DTO
	 * 
	 */
	Converter<List<GrantedAuthority>, List<String>> grantedAuthorityToStringConverter = new Converter<List<GrantedAuthority>, List<String>>() {
		public List<String> convert(MappingContext<List<GrantedAuthority>, List<String>> context) {
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority grantedAuthority : context.getSource()) {
				roles.add(grantedAuthority.getAuthority());
			}
			return roles;
		}
	};

}