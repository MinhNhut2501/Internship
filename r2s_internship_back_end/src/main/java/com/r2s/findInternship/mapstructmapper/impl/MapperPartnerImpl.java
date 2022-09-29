package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.PartnerCreateDTO;
import com.r2s.findInternship.dto.PartnerDTO;
import com.r2s.findInternship.dto.show.PartnerUniversityShow;
import com.r2s.findInternship.entity.Partner;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.mapstructmapper.MapperPartner;
import com.r2s.findInternship.mapstructmapper.MapperUniversity;
import com.r2s.findInternship.mapstructmapper.MapperUser;
@Component
public class MapperPartnerImpl implements MapperPartner {
	@Autowired
	private MapperUser mapperUser;
	@Autowired
	private MapperUniversity mapperUniversity;
	@Override
	public Partner map(PartnerDTO dto) {
		if(dto == null)
		return null;
		Partner entity = new Partner();
		User u;
		if(dto.getUser() != null)
		{
			u = this.mapperUser.map(dto.getUser());
			
		}
		else 
		{
			u = this.mapperUser.map(dto.getUser());
		}
		entity.setId(dto.getId());
		entity.setUser(u);
		entity.setPosition(dto.getPosition());
//		entity.setDemandUni(dto.getDemandUni());
		entity.setUniversity(this.mapperUniversity.map(dto.getUniversityDTO()));

		return entity;
	}

	@Override
	public PartnerDTO map(Partner dto) {
		if(dto == null)
		return null;
		PartnerDTO entity = new PartnerDTO();
		entity.setId(dto.getId());
		entity.setUser(this.mapperUser.mapToUserDetails(dto.getUser()));
		entity.setPosition(dto.getPosition());
//		entity.setDemandUni(dto.getDemandUni());
		entity.setUniversityDTO(this.mapperUniversity.map(dto.getUniversity()));
		return entity;
	}

	@Override
	public Partner map(PartnerCreateDTO dto) {
				return null;
	}
	@Override
	public PartnerUniversityShow mapToUniversityShow(Partner entity)
	{
		if(entity == null) return null;
		return new PartnerUniversityShow(entity.getId(), entity.getUser().getFirstName() + " " + entity.getUser().getLastName(), entity.getUser().getEmail());
	}
	
	
}

