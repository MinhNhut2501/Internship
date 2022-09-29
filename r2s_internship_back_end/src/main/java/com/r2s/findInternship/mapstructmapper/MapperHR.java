package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.HRDTO;
import com.r2s.findInternship.dto.show.HRDTOShow;
import com.r2s.findInternship.entity.HR;

@Mapper(componentModel = "spring")
public interface MapperHR {
	HRDTO map(HR entity);
	HR map(HRDTO dto);
	HRDTOShow mapHRShow(HR entity);

}
