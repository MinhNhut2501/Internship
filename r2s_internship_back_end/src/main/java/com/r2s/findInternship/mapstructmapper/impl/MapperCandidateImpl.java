package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.CandidateCreateDTO;
import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.entity.Candidate;
import com.r2s.findInternship.mapstructmapper.MapperCandidate;
import com.r2s.findInternship.mapstructmapper.MapperMajor;
import com.r2s.findInternship.mapstructmapper.MapperUser;

@Component
public class MapperCandidateImpl implements MapperCandidate {
    @Autowired
    private MapperMajor mapperMajor;
    @Autowired
    private MapperUser mapperUser;

    @Override
    public Candidate map(CandidateDTO dto) {
        if (dto == null) return null;
        Candidate entity = new Candidate();
        entity.setId(dto.getId());
        entity.setMajor(this.mapperMajor.map(dto.getMajor()));
        entity.setUser(mapperUser.map(dto.getUser()));
        entity.setCV(dto.getCV());
        return entity;
    }

    @Override
    public CandidateDTO map(Candidate entity) {
        if (entity == null) return null;

        CandidateDTO dto = new CandidateDTO();
        dto.setId(entity.getId());
        dto.setMajor(this.mapperMajor.map(entity.getMajor()));
        dto.setUser(mapperUser.mapToUserDetails(entity.getUser()));
        dto.setCV(entity.getCV());
        return dto;
    }

    @Override
    public Candidate map(CandidateCreateDTO dto) {
        if (dto == null) return null;
        Candidate entity = new Candidate();
        entity.setId(dto.getId());
        entity.setMajor(this.mapperMajor.map(dto.getMajor()));
        entity.setUser(mapperUser.map(dto.getUser()));
        entity.setCV(dto.getCV());
        return entity;
    }

    @Override
    public CandidateDTO mapShowApplyList(Candidate entity) {
        if (entity == null) return null;

        CandidateDTO dto = new CandidateDTO();
        dto.setId(entity.getId());
        return dto;
    }

}
