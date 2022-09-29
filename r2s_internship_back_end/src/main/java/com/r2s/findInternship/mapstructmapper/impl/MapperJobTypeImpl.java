package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.JobTypeDTO;
import com.r2s.findInternship.entity.JobType;
import com.r2s.findInternship.mapstructmapper.MapperJobType;
@Component
public class MapperJobTypeImpl implements MapperJobType{


	@Override
	public JobTypeDTO map(JobType entity) {
		if(entity == null)
		return null;
		JobTypeDTO dto = new JobTypeDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public JobType map(JobTypeDTO dto) {
		if(dto == null)
			return null;
			JobType entity = new JobType();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			return entity;
	}

}
