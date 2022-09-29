package com.r2s.findInternship.dto.show;

import java.time.LocalDate;

import com.r2s.findInternship.dto.CandidateDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplyListDTOShowNotJob {
	private int id;
	private CandidateDTO candidate;
	private LocalDate createDate;
	private String referenceLetter;
	private String CV;
}
