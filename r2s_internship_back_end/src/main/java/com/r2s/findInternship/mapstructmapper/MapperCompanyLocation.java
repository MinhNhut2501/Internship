package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.CompanyLocationDTO;
import com.r2s.findInternship.entity.CompanyLocation;

@Mapper(componentModel = "spring")
public interface MapperCompanyLocation {
	CompanyLocation map(CompanyLocationDTO dto);
	CompanyLocationDTO map(CompanyLocation entity);
}
