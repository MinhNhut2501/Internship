package com.r2s.findInternship.mapstructmapper.impl;

import com.r2s.findInternship.mapstructmapper.MapperCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.ProvinceDTO;
import com.r2s.findInternship.entity.Province;
import com.r2s.findInternship.mapstructmapper.MapperProvince;

@Component
public class MapperProvinceImpl implements MapperProvince {
	@Autowired
	private MapperCountries mapperCountries;
    @Override
    public Province map(ProvinceDTO dto) {
        if (dto == null)
            return null;
        Province p = new Province();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setShortName(dto.getShortName());
        p.setCountry(mapperCountries.map(dto.getCountries()));
        return p;
    }

    @Override
    public ProvinceDTO map(Province entity) {
        if (entity == null)
            return null;
        ProvinceDTO p = new ProvinceDTO();
        p.setId(entity.getId());
        p.setName(entity.getName());
        p.setShortName(entity.getShortName());
        p.setCountries(mapperCountries.map(entity.getCountry()));
        return p;
    }

}
