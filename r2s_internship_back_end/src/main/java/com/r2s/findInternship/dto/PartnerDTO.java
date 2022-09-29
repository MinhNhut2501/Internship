package com.r2s.findInternship.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PartnerDTO {
	private int id;
	private String position;
	private UserDetailsDTO user;
	private Set<DemandUniDTO> demandUni;
	private UniversityDTO universityDTO;
}
