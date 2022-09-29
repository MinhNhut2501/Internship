package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.JobPositionDTO;
import com.r2s.findInternship.entity.JobPosition;

@Mapper(componentModel = "spring")
public interface MapperJobPosition {
	JobPositionDTO map(JobPosition dto);
	JobPosition map(JobPositionDTO dto);
}
