package com.r2s.findInternship.dto;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.entity.JobType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class DemandUniDTO {
	private int id;
	private String name;
	private PartnerDTO partner;
	private MajorDTO major;
	private String description;
	private JobPositionDTO position;
	private String requirement;
	private String ortherInfo;
	private LocalDate start;
	private LocalDate end;
	private LocalDate update;
	private String startStr;
	private String endStr;
	private String students;
	private LocalDate createDate;
	private MultipartFile file;
	private String updateDateStr;
	private boolean status;
	private JobTypeDTO jobType;
	private long amount;
}
