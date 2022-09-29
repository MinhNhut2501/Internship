package com.r2s.findInternship.mapstructmapper;

import com.r2s.findInternship.dto.CareListDTO;
import com.r2s.findInternship.entity.CareList;

public interface MapperCareList {
    CareList map(CareListDTO dto);
    CareListDTO map(CareList entity);
}
