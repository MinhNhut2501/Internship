package com.r2s.findInternship.mapstructmapper.impl;

import com.r2s.findInternship.mapstructmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.DemandUniDTO;
import com.r2s.findInternship.dto.DemandUniShowDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.entity.DemandUni;

@Component
public class MapperDemandUniImpl implements MapperDemandUni {
    @Autowired
    private MapperMajor mapperMajor;
    @Autowired
    private MapperPartner mapperPartner;
    @Autowired
    private MapperJobPosition mapperJobPosition;
    @Autowired
    private MapperUniversity mapperUniversity;

    @Override
    public DemandUniDTO map(DemandUni entity) {
        if (entity == null)
            return null;
        DemandUniDTO dto = new DemandUniDTO();
        dto.setStudents(entity.getStudents());
        dto.setName(entity.getDesciption());
        dto.setDescription(entity.getDesciption());
        dto.setOrtherInfo(entity.getOrtherInfo());
        dto.setRequirement(entity.getRequirement());
        dto.setMajor(this.mapperMajor.map(entity.getMajor()));
        dto.setPartner(this.mapperPartner.map(entity.getPartner()));
        dto.setPosition(mapperJobPosition.map(entity.getPosition()));
        dto.setStart(entity.getStart());
        dto.setEnd(entity.getEnd());
        dto.setAmount(entity.getAmount());
        dto.setCreateDate(entity.getCreateDate());
        dto.setStatus(entity.isStatus());
        if (entity.getUpdateDate() != null)
            dto.setUpdate(entity.getUpdateDate());
        return dto;
    }

    @Override
    public DemandUni map(DemandUniDTO entity) {
        if (entity == null)
            return null;
        DemandUni dto = new DemandUni();
        dto.setStudents(entity.getStudents());
        dto.setName(entity.getName());
        dto.setDesciption(entity.getDescription());
        dto.setOrtherInfo(entity.getOrtherInfo());
        dto.setRequirement(entity.getRequirement());
        dto.setMajor(this.mapperMajor.map(entity.getMajor()));
        if (entity.getPartner() != null)
            dto.setPartner(this.mapperPartner.map(entity.getPartner()));
        dto.setPosition(mapperJobPosition.map(entity.getPosition()));
        dto.setStart(entity.getStart());
        dto.setEnd(entity.getEnd());
        dto.setAmount(entity.getAmount());
        dto.setCreateDate(entity.getCreateDate());
        if (entity.getUpdate() != null)
            dto.setUpdateDate(entity.getUpdate());
        dto.setStatus(entity.isStatus());
        return dto;
    }

    @Override
    public DemandUniShowDTO mapToShow(DemandUni entity) {
        if (entity == null)
            return null;
        DemandUniShowDTO dto = new DemandUniShowDTO();
        dto.setId(entity.getId());
        dto.setStudents(entity.getStudents());
        if (entity.getPartner().getUniversity() != null) {
//				UniversityDTO universityDTO = new UniversityDTO();
            UniversityDTO universityDTO = this.mapperUniversity.map(entity.getPartner().getUniversity());
//				universityDTO.setId(entity.getPartner().getUniversity().getId());
//				universityDTO.setName(entity.getPartner().getUniversity().getName());	
//				universityDTO.setAddress(entity.getPartner().getUniversity().getUniversity_Location().);
            dto.setUniversityDTO(universityDTO);
        }
        dto.setName(entity.getName());
        dto.setAmount(entity.getAmount());
        dto.setDesciption(entity.getDesciption());
        dto.setOrtherInfo(entity.getOrtherInfo());
        dto.setRequirement(entity.getRequirement());
        dto.setPosition(mapperJobPosition.map(entity.getPosition()));
        dto.setEnd(entity.getEnd());
        dto.setCreateDate(entity.getCreateDate());
        dto.setStatus(entity.isStatus());
        if (entity.getUpdateDate() != null)
            dto.setUpdateDate(entity.getUpdateDate());
        return dto;
    }

}
