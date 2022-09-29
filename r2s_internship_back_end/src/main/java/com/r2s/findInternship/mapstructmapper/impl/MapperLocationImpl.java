package com.r2s.findInternship.mapstructmapper.impl;

import com.r2s.findInternship.mapstructmapper.MapperDistrict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.LocationDTO;
import com.r2s.findInternship.entity.Location;
import com.r2s.findInternship.mapstructmapper.MapperLocation;

@Component
public class MapperLocationImpl implements MapperLocation {
	@Autowired
	private MapperDistrict mapperDistrict;
    @Override
    public Location map(LocationDTO dto) {
        if (dto == null)
            return null;
        Location local = new Location();
        local.setId(dto.getId());
        local.setAddress(dto.getAddress());
        local.setDistrict(mapperDistrict.map(dto.getDistrict()));
        local.setNote(dto.getNote());
        return local;
    }

    @Override
    public LocationDTO map(Location dto) {
        if (dto == null)
            return null;
        LocationDTO local = new LocationDTO();
        local.setId(dto.getId());
        local.setAddress(dto.getAddress());
        local.setDistrict(mapperDistrict.map(dto.getDistrict()));
        local.setNote(dto.getNote());
        return local;
    }

}
