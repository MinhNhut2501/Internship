package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.TypeUniversityDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.entity.TypeUniversity;
import com.r2s.findInternship.entity.University;
import com.r2s.findInternship.mapstructmapper.MapperTypeUniversity;
@Component
public class MapperTypeUniversityImpl implements MapperTypeUniversity{

	@Override
	public TypeUniversity map(TypeUniversityDTO dto) {
		if(dto == null)
		return null;
		TypeUniversity entity = new TypeUniversity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public TypeUniversityDTO map(TypeUniversity entity) {
		if(entity == null)
		return null;
		return new TypeUniversityDTO(entity.getId(), entity.getName());
	}

	@Override
	public TypeUniversityDTO mapUniversityToTypeUniversityDTO(University entity) {
		if (entity == null) {
			return null;
		}
		
		TypeUniversityDTO dto1 = new TypeUniversityDTO();
		dto1.setId(entity.getTypeUniversity().getId());
		dto1.setName(entity.getTypeUniversity().getName());
		return dto1;
	}

}
