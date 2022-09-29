package com.r2s.findInternship.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobPositionDTO {
	private int id;
	private String name;
	private Set<DemandUniDTO> demandUnis;
	private Set<JobDTO> jobs;
}
