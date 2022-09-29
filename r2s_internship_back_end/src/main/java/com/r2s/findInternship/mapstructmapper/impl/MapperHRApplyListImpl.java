package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.HRApplyListDTO;
import com.r2s.findInternship.entity.HRApplyList;
import com.r2s.findInternship.mapstructmapper.MapperDemandUni;
import com.r2s.findInternship.mapstructmapper.MapperHR;
import com.r2s.findInternship.mapstructmapper.MapperHRApplyList;
import com.r2s.findInternship.mapstructmapper.MapperStatus;

@Component
public class MapperHRApplyListImpl implements MapperHRApplyList{
	@Autowired
	MapperHR mapperHR;
	@Autowired
	MapperDemandUni mapperDemandUni;
	@Override
	public HRApplyList map(HRApplyListDTO dto) {
		if(dto == null)return null;
		HRApplyList hrApplyList = new HRApplyList();
		hrApplyList.setId(dto.getId());
		hrApplyList.setDate(dto.getDate());
		hrApplyList.setNote(dto.getNote());
		hrApplyList.setStatus(dto.getStatus());
		hrApplyList.setHr(mapperHR.map(dto.getHr()));
		hrApplyList.setDemandUni(mapperDemandUni.map(dto.getDemandUni()));
		return hrApplyList;
	}

	@Override
	public HRApplyListDTO map(HRApplyList entity) {
		if(entity == null)return null;
		HRApplyListDTO hrApplyListDTO = new HRApplyListDTO();
		hrApplyListDTO.setId(entity.getId());
		hrApplyListDTO.setDate(entity.getDate());
		hrApplyListDTO.setNote(entity.getNote());
		hrApplyListDTO.setStatus(entity.getStatus());
		hrApplyListDTO.setHr(mapperHR.map(entity.getHr()));
		hrApplyListDTO.setDemandUni(mapperDemandUni.map(entity.getDemandUni()));
		return hrApplyListDTO;
	}

}
