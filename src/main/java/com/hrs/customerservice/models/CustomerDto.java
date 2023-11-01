package com.hrs.customerservice.models;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Class representing a Customer in hotel reservation system.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

	@ApiModelProperty(notes = "Unique identifier of the Customer.", example = "1")
	private Long id;

	@ApiModelProperty(notes = "Username of the Customer.", example = "admin", required = true)
	@NotBlank(message="Invalid username: Username may not be blank.")
	private String username;

	@ApiModelProperty(notes = "Password of the Customer.", example = "testpwd", required = true)
	@NotBlank(message="Invalid password: Password may not be blank.")
	private String password;

	@ApiModelProperty(notes = "Roles assigned to the Customer.", example = "ROLE_ADMIN, ROLE_USER", required = true)
	@NotEmpty(message = "Invalid roles: Roles cannot be empty. Atleast one valid role to be assigned to the customer.")
	private List<@NotBlank String> roles;

	@ApiModelProperty(notes = "Name of the Customer.", example = "Admin", required = true)
	@NotBlank(message="Invalid name: Name may not be blank.")
	private String name;

	@ApiModelProperty(notes = "Email address of the Customer.", example = "hrs.admin@gmail.com", required = true)
	@NotBlank(message="Invalid email: Email may not be blank.")
	@Email(message = "Invalid email: Enter valid email.")
	private String email;

	@ApiModelProperty(notes = "Country of the Customer.", example = "India")
	private String country;

}
