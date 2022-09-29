package com.r2s.findInternship.mapstructmapper.impl;

import com.r2s.findInternship.dto.UniversityLocationDTO;
import com.r2s.findInternship.entity.UniversityLocation;
import com.r2s.findInternship.mapstructmapper.MapperLocation;
import com.r2s.findInternship.mapstructmapper.MapperUniversity;
import com.r2s.findInternship.mapstructmapper.MapperUniversityLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperUniversityLocationImpl implements MapperUniversityLocation {
    @Autowired
    private MapperUniversity mapperUniversity;
    @Autowired
    private MapperLocation mapperLocation;

    @Override
    public UniversityLocation map(UniversityLocationDTO dto) {
        if (dto == null) return null;
        UniversityLocation entity = new UniversityLocation();
        entity.setId(dto.getId());
        entity.setUniversity(mapperUniversity.map(dto.getUniversity()));
        entity.setLocation(mapperLocation.map(dto.getLocation()));
        return null;
    }

    @Override
    public UniversityLocationDTO map(UniversityLocation entity) {
        if (entity == null) return null;
        UniversityLocationDTO dto = new UniversityLocationDTO();
        dto.setId(entity.getId());
        dto.setUniversity(mapperUniversity.map(entity.getUniversity()));
        dto.setLocation(mapperLocation.map(entity.getLocation()));
        return null;
    }
}
