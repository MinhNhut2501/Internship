package com.r2s.findInternship.dto;



import java.time.LocalDate;


import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplyListDTO {
	private int id;
	private JobDTO jobApp;
	private CandidateDTO candidate;
	private LocalDate createDate;
	private String referenceLetter;
	private String CV;
	private MultipartFile fileCV;
}
