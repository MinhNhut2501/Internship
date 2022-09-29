package com.r2s.findInternship.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateDTO {

	private int id;	
	private String CV;
	// sửa thành user, lúc trước là userDTO
	private UserDetailsDTO user;
	private MajorDTO major;
	private Set<CareListDTO> careJobs;
}
