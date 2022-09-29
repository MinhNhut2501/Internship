package com.r2s.findInternship.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyLocationDTO {

	private int id;
	private CompanyDTO  companyDTO;
	private LocationDTO locationDTO;
}
