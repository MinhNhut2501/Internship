package com.r2s.findInternship.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDTO {

	private int id;
	private DistrictDTO district;
	private String address;
	private String note;
	
}
