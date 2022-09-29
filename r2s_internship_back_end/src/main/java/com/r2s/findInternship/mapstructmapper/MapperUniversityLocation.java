package com.r2s.findInternship.mapstructmapper;

import com.r2s.findInternship.dto.UniversityLocationDTO;
import com.r2s.findInternship.entity.UniversityLocation;

public interface MapperUniversityLocation {
    UniversityLocation map(UniversityLocationDTO dto);
    UniversityLocationDTO map(UniversityLocation entity);
}
