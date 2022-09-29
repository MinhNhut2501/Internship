package com.r2s.findInternship.dto.show;


import com.r2s.findInternship.dto.CompanyDTO;
import com.r2s.findInternship.dto.UserDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class HRDTOShow {
	private int id;
	private UserDetailsDTO user;
	private String position;
	private CompanyDTO company;
}
