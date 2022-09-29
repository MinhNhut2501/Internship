package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.StatusDTO;
import com.r2s.findInternship.entity.Status;
import com.r2s.findInternship.entity.University;
import com.r2s.findInternship.mapstructmapper.MapperStatus;

@Component
public class MapperStatusImpl implements MapperStatus {

	@Override
	public Status map(StatusDTO dto) {
		if (dto == null) return null;
		Status entity = new Status();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public StatusDTO map(Status entity) {
		if (entity == null) return null;
		StatusDTO dto = new StatusDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public StatusDTO mapUniversityToStatusDTO(University entity) {
		if (entity == null) {
			return null;
		}
		StatusDTO dto1 = new StatusDTO();
		dto1.setId(entity.getStatus().getId());
		dto1.setName(entity.getStatus().getName());
		return dto1;
	}
	
}
