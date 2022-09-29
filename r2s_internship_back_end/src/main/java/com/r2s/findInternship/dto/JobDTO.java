package com.r2s.findInternship.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobDTO {

	private int id;
	private String name;
	private HRDTO hr;
	private String description;
	private MajorDTO major;
	private JobTypeDTO jobType;
	private JobPositionDTO jobposition;
	private int amount;
	private long salaryMin;
	private long salaryMax;
	private String requirement;
	private String otherInfo;
	private String timeStartStr;
	private String timeEndStr;
	private LocationDTO locationjob;
	private LocalDate createDate;
	private StatusDTO status;
	private int numOfApply;

}
