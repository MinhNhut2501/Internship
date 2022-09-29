package com.r2s.findInternship.mapstructmapper.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.show.JobDTOShow;
import com.r2s.findInternship.entity.Job;
import com.r2s.findInternship.mapstructmapper.MapperHR;
import com.r2s.findInternship.mapstructmapper.MapperJob;
import com.r2s.findInternship.mapstructmapper.MapperJobPosition;
import com.r2s.findInternship.mapstructmapper.MapperJobType;
import com.r2s.findInternship.mapstructmapper.MapperLocation;
import com.r2s.findInternship.mapstructmapper.MapperMajor;
import com.r2s.findInternship.mapstructmapper.MapperStatus;
import com.r2s.findInternship.repository.ApplyListRepository;
import com.r2s.findInternship.service.CompanyService;

@Component
public class MapperJobImpl implements MapperJob{
	@Autowired
	private MapperJobPosition mapperJob;
	@Autowired 
	private MapperMajor mapperMajor;
	@Autowired 
	private MapperLocation mapperLocation;
	@Autowired 
	private MapperStatus mapperStatus;
	@Autowired 
	private MapperJobType mapperJobType;
	@Autowired 
	private CompanyService companyService;
	@Autowired
	private MapperHR mapperHR;
	@Autowired
	private ApplyListRepository applyListRepository;
	

	@Override
	public Job map(JobDTO dto) {
		if(dto == null)
		return null;
		Job entity = new Job();
		entity.setId(dto.getId());
		entity.setDesciption(dto.getDescription());
		entity.setJobposition(mapperJob.map(dto.getJobposition()));
		entity.setJobType(mapperJobType.map(dto.getJobType()));
		entity.setName(dto.getName());
		entity.setOtherInfo(dto.getOtherInfo());
		entity.setRequirement(dto.getRequirement());
		entity.setSalaryMin(dto.getSalaryMin());
		entity.setSalaryMax(dto.getSalaryMax());
		entity.setStatus(mapperStatus.map(dto.getStatus()));
		entity.setMajor(this.mapperMajor.map(dto.getMajor()));
		//LocalDate to String
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
		        .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
		entity.setTimeStart(LocalDate.parse(dto.getTimeStartStr(),formatter));
		entity.setTimeEnd(LocalDate.parse(dto.getTimeEndStr(),formatter));
		entity.setCreateDate(dto.getCreateDate());
		entity.setLocationjob(mapperLocation.map(dto.getLocationjob()));
		entity.setHr(mapperHR.map(dto.getHr()));
		entity.setHr(this.mapperHR.map(dto.getHr()));
		entity.setJobposition(this.mapperJob.map(dto.getJobposition()));
		entity.setAmount(dto.getAmount());
		return entity;
		
	}

	@Override
	public JobDTO map(Job entity) {
		if(entity == null)
		return null;
		JobDTO dto = new JobDTO();
		dto.setId(entity.getId());
		dto.setDescription(entity.getDesciption());
		dto.setJobposition(this.mapperJob.map(entity.getJobposition()));
		dto.setJobType(mapperJobType.map(entity.getJobType()));
		dto.setName(entity.getName());
		dto.setOtherInfo(entity.getOtherInfo());
		dto.setRequirement(entity.getRequirement());
		dto.setSalaryMin(entity.getSalaryMin());
		dto.setSalaryMax(entity.getSalaryMax());
		dto.setStatus(mapperStatus.map(entity.getStatus()));
		dto.setMajor(this.mapperMajor.map(entity.getMajor()));
		//LocalDate to String
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(entity.getTimeStart()!=null && entity.getTimeEnd() != null)
		{
			dto.setTimeStartStr(entity.getTimeStart().format(format));
			dto.setTimeEndStr(entity.getTimeEnd().format(format));
		}
		dto.setCreateDate(entity.getCreateDate());
		dto.setLocationjob(mapperLocation.map(entity.getLocationjob()));
		dto.setHr(mapperHR.map(entity.getHr()));
		dto.setHr(this.mapperHR.map(entity.getHr()));
		dto.setJobposition(this.mapperJob.map(entity.getJobposition()));
		dto.setAmount(entity.getAmount());
		dto.setNumOfApply(applyListRepository.getNumOfApplyByJobId(entity.getId()));
		return dto;
	}

	@Override
	public JobDTOShow mapJobShow(Job entity) {
		if(entity == null)
			return null;
			JobDTOShow dto = new JobDTOShow();
			dto.setId(entity.getId());
			dto.setDescription(entity.getDesciption());
			dto.setJobposition(entity.getJobposition().getName());
			dto.setJobType(entity.getJobType().getName());
			dto.setName(entity.getName());
			dto.setOtherInfo(entity.getOtherInfo());
			dto.setRequirement(entity.getRequirement());
			dto.setSalaryMin(entity.getSalaryMin());
			dto.setSalaryMax(entity.getSalaryMax());
			dto.setStatus(entity.getStatus().getId());
			dto.setMajor(entity.getMajor().getName());
			dto.setCompany(companyService.getByJobId(entity.getId()));
			//LocalDate to String
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			if(entity.getTimeStart()!=null && entity.getTimeEnd() != null)
			{
				dto.setTimeStartStr(entity.getTimeStart().format(format));
				dto.setTimeEndStr(entity.getTimeEnd().format(format));
			}
			dto.setCreateDate(entity.getCreateDate());
			dto.setLocationjob(entity.getLocationjob().show());
			dto.setJobposition(entity.getJobposition().getName());
			dto.setAmount(entity.getAmount());
			return dto;
	}
	
}
