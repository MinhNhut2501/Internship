package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.dto.show.ApplyListDTOShowNotCandidate;
import com.r2s.findInternship.dto.show.ApplyListDTOShowNotJob;
import com.r2s.findInternship.entity.ApplyList;

@Mapper(componentModel = "spring")
public interface MapperApplyList {
	ApplyList map(ApplyListDTO dto);
	ApplyListDTO map(ApplyList entity);
	ApplyListDTOShowNotCandidate mapDtoShow(ApplyList entity);
	ApplyListDTOShowNotJob mapDtoShowNotJob(ApplyList entity);
}
