package com.r2s.findInternship.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UniversityDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
//	@NotEmpty(message = "{error.UniversityName}")
	private String name;
	private String avatar;
//	@NotEmpty(message = "{error.UniversityShortName}")
	private String shortName;
//	@NotEmpty(message = "{error.UniversityDescription}")
	private String description;	
//	@NotNull(message = "{error.UniversityWeb}")
	private String website;
//	@NotEmpty(message = "{error.UniversityEmail}")
//	@Email(message =  "{error.emailFormat}")
	private String email;
	private String phone;
	private TypeUniversityDTO type;
	private LocalDate createDate;
	private StatusDTO status;
}
