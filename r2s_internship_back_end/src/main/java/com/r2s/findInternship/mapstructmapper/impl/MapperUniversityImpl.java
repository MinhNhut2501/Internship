package com.r2s.findInternship.mapstructmapper.impl;

import java.util.*;

import com.r2s.findInternship.dto.LocationDTO;
import com.r2s.findInternship.dto.UniversityLocationDTO;
import com.r2s.findInternship.entity.UniversityLocation;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.UniversityCreateDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.entity.University;
import com.r2s.findInternship.repository.UniversityLocationRepository;

@Component
public class MapperUniversityImpl implements MapperUniversity {
    @Autowired
    private MapperMajor mapperMajor;
    @Autowired
    private MapperPartner mapperPartner;
    @Autowired
    private MapperUniversityLocation mapperUniversityLocation;
    @Autowired
    private MapperStatus mapperStatus;
    @Autowired
    private UniversityLocationRepository universityLocationRepository;
    @Autowired
    private MapperTypeUniversity mapperTypeUniversity;

    @Override
    public University map(UniversityDTO dto) {
        if (dto == null) return null;
        University entity = new University();
        entity.setId(dto.getId());
        entity.setAvatar(dto.getAvatar());
        entity.setDescription(dto.getDescription());
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setShortName(dto.getShortName());
        entity.setTypeUniversity(this.mapperTypeUniversity.map(dto.getType()));
        entity.setStatus(this.mapperStatus.map(dto.getStatus()));
        entity.setWebsite(dto.getWebsite());
        /*
         * if(!dto.getPartnerDTO().isEmpty()) {
         * entity.setPartners(dto.getPartnerDTO().stream().map(item ->
         * this.mapperPartner.map(item)).collect(Collectors.toSet())); }
         */
//		if(dto.getUniversity_Locations().size()>0)
//			entity.setUniversity_Location(dto.getUniversity_Locations());
        return entity;
    }

    @Override
    public UniversityDTO map(University entity) {
        if (entity == null)
            return null;
        UniversityDTO dto = new UniversityDTO();
        dto.setId(entity.getId());
        dto.setAvatar(entity.getAvatar());
        dto.setDescription(entity.getDescription());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setShortName(entity.getShortName());
        dto.setStatus(this.mapperStatus.map(entity.getStatus()));
        dto.setType(this.mapperTypeUniversity.map(entity.getTypeUniversity()));
        dto.setWebsite(entity.getWebsite());
        return dto;
    }

    @Override
    public University map(UniversityCreateDTO dto) {
        if (dto == null)
            return null;
        University entity = new University();
        entity.setId(dto.getId());
        entity.setAvatar(dto.getAvatar());
        entity.setDescription(dto.getDescription());
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setShortName(dto.getShortName());
        entity.setStatus(this.mapperStatus.map(dto.getStatus()));
        entity.setTypeUniversity(this.mapperTypeUniversity.map(dto.getType()));
        entity.setWebsite(dto.getWebsite());
        //NO PARTNER
//		if(dto.getPartnerCreateDTO() != null))
//		{
//			Set<Partner> sets = new HashSet<Partner>();
//			sets.add(null)
//		}
//		if(dto.getLocation().size()>0)
//		{
//			entity.setLocations(dto.getLocation());
//		}
//		if(dto.getUniversity_Locations().size()>0)
//			entity.setUniversity_Location(dto.getUniversity_Locations());
        return entity;
    }

	@Override
	public UniversityCreateDTO mapToCreateDTO(University entity) {
		if (entity == null)
            return null;
        UniversityCreateDTO dto1 = new UniversityCreateDTO();
        //dto1.setPartnerCreateDTO();
        dto1.setId(entity.getId());
        dto1.setAvatar(entity.getAvatar());
        dto1.setDescription(entity.getDescription());
        dto1.setEmail(entity.getEmail());
        dto1.setName(entity.getName());
        dto1.setPhone(entity.getPhone());
        dto1.setShortName(entity.getShortName());
        dto1.setStatus(this.mapperStatus.mapUniversityToStatusDTO(entity));
        dto1.setType(this.mapperTypeUniversity.mapUniversityToTypeUniversityDTO(entity));
        dto1.setWebsite(entity.getWebsite());
        return dto1;

	}

}
