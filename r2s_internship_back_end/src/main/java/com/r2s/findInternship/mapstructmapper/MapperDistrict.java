package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.DistrictDTO;
import com.r2s.findInternship.entity.District;

@Mapper(componentModel = "spring")
public interface MapperDistrict {
	District map(DistrictDTO dto);
	DistrictDTO map(District entity);
}
