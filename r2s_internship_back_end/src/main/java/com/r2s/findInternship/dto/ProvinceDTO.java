package com.r2s.findInternship.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProvinceDTO {
	private int id;
	private String name;
	private String shortName;
	private CountriesDTO countries;
}
