package com.r2s.findInternship.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.entity.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UniversityCreateDTO extends UniversityDTO{
	private static final long serialVersionUID = 1L;
	private MultipartFile file;
	private PartnerCreateDTO partnerCreateDTO;
	@NotNull(message = "{error.locationNotNull}")
	private List<LocationDTO> location = new ArrayList<>();
}
