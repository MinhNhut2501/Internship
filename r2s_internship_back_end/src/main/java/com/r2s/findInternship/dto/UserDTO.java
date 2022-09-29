package com.r2s.findInternship.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.r2s.findInternship.entity.Role;
import com.r2s.findInternship.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	private long id;
	@Size(min = 6,message = "{error.usernameRequire}")
	@NotEmpty(message = "{error.usernameNotNull}")
	private String username;
	private int gender;
	private String phone;
	@Email(message = "{error.emailFormat}")
	@NotEmpty(message = "{error.emailNotNull}")
	private String email;
	private Status status;
	private Role role;
}
