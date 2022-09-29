
package com.r2s.findInternship.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDTO {
	private int id;
	private String name;
	private String logo;
	private MultipartFile fileLogo;
	private String description;
	private String website;
	private String email;
	private String phone;
	private String tax;
	private LocalDate date;
	private StatusDTO status;
}
