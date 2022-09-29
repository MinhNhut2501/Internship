package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.MajorDTO;
import com.r2s.findInternship.entity.Major;

@Mapper(componentModel = "spring")
public interface MapperMajor {
	Major map(MajorDTO dto);
	MajorDTO map(Major dto);
	
}
