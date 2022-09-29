package com.r2s.findInternship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HRDTO {
	private int id;
	private UserCreationDTO user;
	private String position;
	private CompanyDTO company;
}
