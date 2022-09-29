package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.JobTypeDTO;
import com.r2s.findInternship.entity.JobType;

@Mapper(componentModel = "spring")
public interface MapperJobType {
	JobTypeDTO map(JobType entity);
	JobType map(JobTypeDTO dto);

}
