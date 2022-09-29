package com.r2s.findInternship.mapstructmapper.impl;

import com.r2s.findInternship.mapstructmapper.MapperProvince;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.DistrictDTO;
import com.r2s.findInternship.entity.District;
import com.r2s.findInternship.mapstructmapper.MapperDistrict;

@Component
public class MapperDistrictImpl implements MapperDistrict {
	@Autowired
	private MapperProvince mapperProvince;

    @Override
    public District map(DistrictDTO dto) {
        if (dto == null)
            return null;
        District d = new District();
        d.setId(dto.getId());
        d.setName(dto.getName());
        d.setProvince(mapperProvince.map(dto.getProvince()));

        return d;
    }

    @Override
    public DistrictDTO map(District dto) {
        if (dto == null)
            return null;
        DistrictDTO d = new DistrictDTO();
        d.setId(dto.getId());
        d.setName(dto.getName());
        d.setProvince(mapperProvince.map(dto.getProvince()));
        return d;
    }

}
