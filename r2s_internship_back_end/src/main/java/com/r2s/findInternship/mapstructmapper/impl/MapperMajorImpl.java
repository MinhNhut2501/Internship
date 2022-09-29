package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.MajorDTO;
import com.r2s.findInternship.entity.Major;
import com.r2s.findInternship.mapstructmapper.MapperMajor;
@Component
public class MapperMajorImpl implements MapperMajor {

	@Override
	public Major map(MajorDTO dto) {
		if(dto == null) return null;
		Major entity = new Major();
		if(dto.getId() != 0) entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public MajorDTO map(Major entity) {
		if(entity == null) return null;
		MajorDTO dto = new MajorDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

}
