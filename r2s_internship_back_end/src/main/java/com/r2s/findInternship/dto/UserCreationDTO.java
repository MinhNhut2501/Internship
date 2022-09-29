package com.r2s.findInternship.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreationDTO  extends UserDetailsDTO{

	@Size(min = 6,message = "{error.passwordRequire}")
	@NotEmpty(message = "{error.passwordNotNull}")
	private String password;
	private String confirmPassword;
	
}
