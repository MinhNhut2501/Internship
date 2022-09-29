package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.UniversityCreateDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.entity.University;

@Mapper(componentModel = "spring")
public interface MapperUniversity {
	University map(UniversityDTO dto);
	UniversityDTO map(University entity);
	University map(UniversityCreateDTO dto);
	UniversityCreateDTO mapToCreateDTO(University entity);
}
