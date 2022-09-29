package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.HRDTO;
import com.r2s.findInternship.dto.show.HRDTOShow;
import com.r2s.findInternship.entity.HR;
import com.r2s.findInternship.mapstructmapper.MapperCompany;
import com.r2s.findInternship.mapstructmapper.MapperHR;
import com.r2s.findInternship.mapstructmapper.MapperUser;

@Component
public class MapperHRImpl implements MapperHR {
	
	@Autowired
	private MapperUser mapperUser;
	@Autowired
	MapperCompany mapperCompany;
	
	@Override
	public HR map(HRDTO dto) {
		if(dto == null) return null;
		HR entity = new HR();
		entity.setId(dto.getId());
		entity.setPosition( dto.getPosition());
		entity.setUser(mapperUser.map(dto.getUser()));
		entity.setCompany(mapperCompany.map(dto.getCompany()));
		return entity;
		
	}
	
	

	@Override
	public HRDTO map(HR entity) {
		if(entity == null) return null;
		HRDTO dto = new HRDTO();
		dto.setId(entity.getId());
		dto.setPosition( entity.getPosition());
		dto.setUser(mapperUser.mapToCreate(entity.getUser()));
		dto.setCompany(mapperCompany.map(entity.getCompany()));
		return dto;
	}



	@Override
	public HRDTOShow mapHRShow(HR entity) {
		if(entity == null) return null;
		HRDTOShow dto = new HRDTOShow();
		dto.setId(entity.getId());
		dto.setPosition( entity.getPosition());
		dto.setUser(mapperUser.mapToUserDetails(entity.getUser()));
		dto.setCompany(mapperCompany.map(entity.getCompany()));
		return dto;
	}

}
