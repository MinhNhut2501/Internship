package com.r2s.findInternship.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.r2s.findInternship.entity.Role;

public class UserRegisterDTO {
	@Size(min = 6,message = "{error.usernameRequire}")
	@NotEmpty(message = "{error.usernameNotNull}")
	private String username;
	@Size(min = 6,message = "{error.passwordRequire}")
	@NotEmpty(message = "{error.passwordNotNull}")
	private String password;
	private String confirmPassword;
	private Role role;
}
