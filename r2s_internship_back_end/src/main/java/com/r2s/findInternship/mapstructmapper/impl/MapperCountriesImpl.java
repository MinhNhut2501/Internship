package com.r2s.findInternship.mapstructmapper.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.CountriesDTO;
import com.r2s.findInternship.entity.Countries;
import com.r2s.findInternship.mapstructmapper.MapperCountries;
import com.r2s.findInternship.mapstructmapper.MapperProvince;

@Component
public class MapperCountriesImpl implements MapperCountries {

    @Override
    public Countries map(CountriesDTO dto) {
        if (dto == null)
            return null;
        Countries entity = new Countries();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAreaCode(dto.getAreaCode());
        return entity;
    }

    @Override
    public CountriesDTO map(Countries entity) {
        if (entity == null)
            return null;
        CountriesDTO dto = new CountriesDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAreaCode(entity.getAreaCode());
        return dto;
    }

}
