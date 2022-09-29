package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.CompanyLocationDTO;
import com.r2s.findInternship.entity.CompanyLocation;
import com.r2s.findInternship.mapstructmapper.MapperCompany;
import com.r2s.findInternship.mapstructmapper.MapperCompanyLocation;
import com.r2s.findInternship.mapstructmapper.MapperLocation;
@Component
public class MapperCompanyLocationImpl implements MapperCompanyLocation {

	
	@Autowired
	MapperLocation mapperLocation;
	@Autowired
	MapperCompany mapperCompany;
	
	@Override
	public CompanyLocation map(CompanyLocationDTO dto) {
		if(dto == null)
			return null;
		CompanyLocation entity = new CompanyLocation();
		entity.setId(dto.getId());
		entity.setCompany(mapperCompany.map(dto.getCompanyDTO()));
		entity.setLocation(mapperLocation.map(dto.getLocationDTO()));
		return entity;
	}

	@Override
	public CompanyLocationDTO map(CompanyLocation entity) {
		if(entity == null)
			return null;
		CompanyLocationDTO dto = new CompanyLocationDTO();
		dto.setId(entity.getId());
		dto.setCompanyDTO(mapperCompany.map(entity.getCompany()));
		dto.setLocationDTO(mapperLocation.map(entity.getLocation()));
		return dto;
	}

}
