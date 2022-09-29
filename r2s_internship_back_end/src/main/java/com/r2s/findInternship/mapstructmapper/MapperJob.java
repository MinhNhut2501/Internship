package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.show.JobDTOShow;
import com.r2s.findInternship.entity.Job;

@Mapper(componentModel = "spring")
public interface MapperJob {
	Job map(JobDTO dto);
	JobDTO map(Job entity);
	JobDTOShow mapJobShow(Job entity);
}
