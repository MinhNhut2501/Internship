package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.CandidateCreateDTO;
import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.entity.Candidate;

@Mapper(componentModel = "spring")
public interface MapperCandidate {
	Candidate map(CandidateDTO dto);
	CandidateDTO map(Candidate entity);
	Candidate map(CandidateCreateDTO dto);
	CandidateDTO mapShowApplyList(Candidate entity);
	

}
