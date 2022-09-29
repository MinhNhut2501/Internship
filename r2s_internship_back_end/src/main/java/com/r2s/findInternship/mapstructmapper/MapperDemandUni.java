package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.DemandUniDTO;
import com.r2s.findInternship.dto.DemandUniShowDTO;
import com.r2s.findInternship.entity.DemandUni;

@Mapper(componentModel = "spring")
public interface MapperDemandUni {
	DemandUniDTO map(DemandUni entity);
	DemandUni map(DemandUniDTO entity);
	DemandUniShowDTO mapToShow(DemandUni entity);
}
