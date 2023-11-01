package com.hrs.customerservice.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hrs.customerservice.services.CustomerAuthenticationService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomerAuthenticationService customerAuthenticationService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api/**").permitAll().antMatchers("/**").authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
				.requestMatchers(new AntPathRequestMatcher("/swagger-resources/**"))
				.requestMatchers(new AntPathRequestMatcher("/swagger-ui.html"))
				.requestMatchers(new AntPathRequestMatcher("/v2/api-docs"))
				.requestMatchers(new AntPathRequestMatcher("/webjars/**"));
	}

	@Override
	@Autowired
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerAuthenticationService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
