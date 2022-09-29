package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.ProvinceDTO;
import com.r2s.findInternship.entity.Province;

@Mapper(componentModel = "spring")
public interface MapperProvince {
	Province map(ProvinceDTO dto);
	ProvinceDTO map(Province dto);
}
