package com.r2s.findInternship.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CareListDTO {
	private int id;
	private JobDTO jobCare;
	private CandidateDTO candidateCare;
	private LocalDate createDate;
	private String note;
	
}
