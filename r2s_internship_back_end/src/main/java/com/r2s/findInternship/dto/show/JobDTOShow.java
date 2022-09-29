package com.r2s.findInternship.dto.show;

import java.time.LocalDate;

import com.r2s.findInternship.dto.CompanyDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobDTOShow {
	private int id;
	private String name;
	private String description;
	private String major;
	private String jobType;
	private String jobposition;
	private int amount;
	private long salaryMin;
	private long salaryMax;
	private String requirement;
	private String otherInfo;
	private String timeStartStr;
	private String timeEndStr;
	private String locationjob;
	private LocalDate createDate;
	private int status;
	private CompanyDTO company;
}
