package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.HRApplyListDTO;
import com.r2s.findInternship.entity.HRApplyList;

@Mapper(componentModel = "spring")
public interface MapperHRApplyList {
	HRApplyList map(HRApplyListDTO dto);
	HRApplyListDTO map(HRApplyList enity);
}
