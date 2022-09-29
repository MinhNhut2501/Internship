package com.r2s.findInternship.mapstructmapper;

import com.r2s.findInternship.dto.StatusDTO;
import com.r2s.findInternship.entity.Status;
import com.r2s.findInternship.entity.University;

public interface MapperStatus {
	Status map(StatusDTO dto);
	StatusDTO map(Status entity);
	StatusDTO mapUniversityToStatusDTO(University entity);
}
