package com.r2s.findInternship.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateCreateDTO extends CandidateDTO {
	private UserCreationDTO createUser;
	private MultipartFile fileCV;
}
