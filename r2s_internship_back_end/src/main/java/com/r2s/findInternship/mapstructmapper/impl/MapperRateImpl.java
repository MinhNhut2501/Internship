package com.r2s.findInternship.mapstructmapper.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.RateDTO;
import com.r2s.findInternship.entity.Rate;
import com.r2s.findInternship.mapstructmapper.MapperCompany;
import com.r2s.findInternship.mapstructmapper.MapperRate;
import com.r2s.findInternship.mapstructmapper.MapperStatus;
import com.r2s.findInternship.mapstructmapper.MapperUser;
import com.r2s.findInternship.service.StatusService;

@Component
public class MapperRateImpl implements MapperRate {
    @Autowired
    private StatusService statusService;
    @Autowired
    private MapperUser mapperUser;
    @Autowired
    private MapperCompany mapperCompany;
    @Autowired
    private MapperStatus mapperStatus;

    @Override
    public Rate map(RateDTO dto) {
        if (dto == null) return null;
        Rate entity = new Rate();
        entity.setTitle(dto.getTitle());
        entity.setComment(dto.getComment());
        entity.setScore(dto.getScore());
        entity.setCreateDate(dto.getCreateDate());
        entity.setStatus(this.mapperStatus.map(dto.getStatus()));
        entity.setCompany(this.mapperCompany.map(dto.getCompany()));
        entity.setUser(this.mapperUser.map(dto.getUser()));
        entity.setHide(dto.isHide());
        return entity;
    }

    @Override
    public RateDTO map(Rate entity) {
        if (entity == null) return null;
        RateDTO dto = new RateDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setComment(entity.getComment());
        dto.setCreateDate(entity.getCreateDate());
        dto.setScore(entity.getScore());
        dto.setStatus(this.mapperStatus.map(entity.getStatus()));
        dto.setCompany(this.mapperCompany.map(entity.getCompany()));
        dto.setUser(this.mapperUser.map(entity.getUser()));
        dto.setHide(entity.isHide());
        return dto;
    }

    @Override
    public Rate mapUpdate(Rate oldEntity, RateDTO dto) {
        if (dto == null) return null;
        Rate newEntity = new Rate();
        newEntity.setTitle(dto.getTitle() == null || dto.getTitle().isEmpty() ? oldEntity.getTitle(): dto.getTitle());
        newEntity.setHide(dto.isHide());
        newEntity.setScore(dto.getScore());
        newEntity.setCreateDate(dto.getCreateDate());
        newEntity.setComment(dto.getComment());
        newEntity.setId(oldEntity.getId());
        newEntity.setStatus(oldEntity.getStatus());
        newEntity.setUser(oldEntity.getUser());
        newEntity.setCompany(oldEntity.getCompany());
        return newEntity;
    }
}
