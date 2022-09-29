package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.JobPositionDTO;
import com.r2s.findInternship.entity.JobPosition;
import com.r2s.findInternship.mapstructmapper.MapperJob;
import com.r2s.findInternship.mapstructmapper.MapperJobPosition;
@Component
public class MapperJobPositionImpl implements MapperJobPosition {
	@Autowired
	private MapperJob mapperJob;
	@Override
	public JobPositionDTO map(JobPosition entity) {
		if(entity == null)
		return null;
		JobPositionDTO dto = new JobPositionDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
//		entity.setJobs(mapperJob.map(dto.getJobs()));
//		entity.setDemandUnis(dto.getDemandUnis());
		return dto;
	}

	@Override
	public JobPosition map(JobPositionDTO dto) {
		if(dto == null)
			return null;
			JobPosition entity = new JobPosition();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
//			entity.setJobs(dto.getJobs());
//			entity.setDemandUnis(dto.getDemandUnis());
			return entity;
	}

}
