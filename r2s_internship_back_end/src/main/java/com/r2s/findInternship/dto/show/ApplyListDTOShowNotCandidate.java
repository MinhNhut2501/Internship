package com.r2s.findInternship.dto.show;

import java.time.LocalDate;

import com.r2s.findInternship.dto.JobDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplyListDTOShowNotCandidate {
	private int id;
	private JobDTO job;
	private LocalDate createDate;
	private String referenceLetter;
	private String CV;

}
