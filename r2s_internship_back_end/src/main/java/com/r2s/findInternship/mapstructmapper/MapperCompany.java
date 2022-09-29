package com.r2s.findInternship.mapstructmapper;


import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.CompanyDTO;
import com.r2s.findInternship.entity.Company;

@Mapper(componentModel = "spring")
public interface MapperCompany {
	Company map(CompanyDTO dto);
	CompanyDTO map(Company dto);

}
