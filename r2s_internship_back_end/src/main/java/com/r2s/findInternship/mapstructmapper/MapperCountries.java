package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.CountriesDTO;
import com.r2s.findInternship.entity.Countries;

@Mapper(componentModel = "spring")
public interface MapperCountries {
	Countries map(CountriesDTO dto);
	CountriesDTO map(Countries entity);

}
