package com.r2s.findInternship.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DemandUniShowDTO {

	private int id;
	private String name;
	private String desciption;
	private JobPositionDTO position;
	private String requirement;
	private String ortherInfo;
	private LocalDate end;
	private String students;
	private LocalDate createDate;
	private LocalDate updateDate;
	private UniversityDTO universityDTO;
	private String jobTypes;
	private long amount;
	// thêm vào status để hiển thị
	private boolean status;

}














