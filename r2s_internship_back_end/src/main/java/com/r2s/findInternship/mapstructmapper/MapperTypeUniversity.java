package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.TypeUniversityDTO;
import com.r2s.findInternship.entity.TypeUniversity;
import com.r2s.findInternship.entity.University;

@Mapper(componentModel = "spring")
public interface MapperTypeUniversity {
	TypeUniversity map(TypeUniversityDTO dto);
	TypeUniversityDTO map(TypeUniversity dto);
	TypeUniversityDTO mapUniversityToTypeUniversityDTO(University entity);
}
