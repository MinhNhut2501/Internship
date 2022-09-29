package com.r2s.findInternship.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePassDTO {
	@NotNull(message = "{error.userNewPassword}")
	private String newPassword;
	@NotNull(message = "{error.userOldPassword}")
	private String oldPassword;
}
