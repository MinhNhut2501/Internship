package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.dto.show.ApplyListDTOShowNotCandidate;
import com.r2s.findInternship.dto.show.ApplyListDTOShowNotJob;
import com.r2s.findInternship.entity.ApplyList;
import com.r2s.findInternship.mapstructmapper.MapperApplyList;
import com.r2s.findInternship.mapstructmapper.MapperCandidate;
import com.r2s.findInternship.mapstructmapper.MapperJob;
import com.r2s.findInternship.mapstructmapper.MapperStatus;

@Component
public class MapperApplyListImpl implements MapperApplyList{
	@Autowired
	private MapperJob mapperJob;
	@Autowired
	MapperStatus mapperStatus;
	@Autowired
	private MapperCandidate mapperCandidate;

	@Override
	public ApplyList map(ApplyListDTO dto) {
		if(dto == null)
			return null;
			ApplyList entity = new ApplyList();
			entity.setId(dto.getId());
			entity.setCreateDate(dto.getCreateDate());
			entity.setReferenceLetter(dto.getReferenceLetter());
			entity.setCandidate(mapperCandidate.map(dto.getCandidate()));
			entity.setJobApp(mapperJob.map(dto.getJobApp()));
			entity.setCV(dto.getCV());
			return entity;
	}

	@Override
	public ApplyListDTO map(ApplyList entity) {
		if(entity == null)
			return null;
			ApplyListDTO dto = new ApplyListDTO();
			dto.setId(entity.getId());
			dto.setCreateDate(entity.getCreateDate());
			dto.setReferenceLetter(entity.getReferenceLetter());
			dto.setCandidate(mapperCandidate.map(entity.getCandidate()));
			dto.setJobApp(mapperJob.map(entity.getJobApp()));
			dto.setCV(entity.getCV());
			return dto;
	}

	@Override
	public ApplyListDTOShowNotJob mapDtoShowNotJob(ApplyList entity) {
		if(entity == null)
			return null;
			ApplyListDTOShowNotJob dto = new ApplyListDTOShowNotJob();
			dto.setId(entity.getId());
			dto.setCreateDate(entity.getCreateDate());
			dto.setReferenceLetter(entity.getReferenceLetter());
			dto.setCandidate(mapperCandidate.map(entity.getCandidate()));
			dto.setCV(entity.getCV());
			return dto;
	}

	@Override
	public ApplyListDTOShowNotCandidate mapDtoShow(ApplyList entity) {
		if(entity == null)
			return null;
			ApplyListDTOShowNotCandidate dto = new ApplyListDTOShowNotCandidate();
			dto.setId(entity.getId());
			dto.setCreateDate(entity.getCreateDate());
			dto.setReferenceLetter(entity.getReferenceLetter());
			dto.setJob(mapperJob.map(entity.getJobApp()));
			dto.setCV(entity.getCV());
			return dto;
	}

}
