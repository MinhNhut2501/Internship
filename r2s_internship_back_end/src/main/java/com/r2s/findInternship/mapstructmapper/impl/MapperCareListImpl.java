package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.CareListDTO;
import com.r2s.findInternship.entity.CareList;
import com.r2s.findInternship.mapstructmapper.MapperCandidate;
import com.r2s.findInternship.mapstructmapper.MapperCareList;
import com.r2s.findInternship.mapstructmapper.MapperJob;

@Component
public class MapperCareListImpl implements MapperCareList {
    @Autowired
    private MapperJob mapperJob;
    @Autowired
    private MapperCandidate mapperCandidate;

    @Override
    public CareList map(CareListDTO dto) {
        if (dto == null) return null;
        CareList entity = new CareList();
        entity.setCreateDate(dto.getCreateDate());
        entity.setNote(dto.getNote());
        entity.setJobCare(this.mapperJob.map(dto.getJobCare()));
        entity.setCandidateCare(this.mapperCandidate.map(dto.getCandidateCare()));
        return entity;
    }

    @Override
    public CareListDTO map(CareList entity) {
        if (entity == null) return null;
        CareListDTO dto = new CareListDTO();
        dto.setId(entity.getId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setNote(entity.getNote());
        dto.setJobCare(this.mapperJob.map(entity.getJobCare()));
        dto.setCandidateCare(this.mapperCandidate.map(entity.getCandidateCare()));
        return dto;
    }
}
