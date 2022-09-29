package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.dto.JobPositionDTO;
import com.r2s.findInternship.entity.JobPosition;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperJobPosition;
import com.r2s.findInternship.repository.JobPositionRepository;
import com.r2s.findInternship.service.JobPositionService;
@Service
public class JobPositionServiceImpl implements JobPositionService {
	@Autowired
	private JobPositionRepository jobPositionRepository;
	@Autowired
	private MapperJobPosition mapperJobPosition;
	@Override
	public JobPositionDTO save(JobPositionDTO dto) {
		if(this.existsByName(dto.getName())) throw new InternalServerErrorException(String.format("Job Position is exists in my system wiht %s, so you cann't add it", dto.getName()));
		JobPosition entity = jobPositionRepository.save(mapperJobPosition.map(dto));
		return this.mapperJobPosition.map(jobPositionRepository.save(entity));
	}

	@Override
	public List<JobPositionDTO> findAll() {
		return jobPositionRepository.findAll(Sort.by(Order.by("name"))).stream().map(JobPosition -> this.mapperJobPosition.map(JobPosition)).collect(Collectors.toList());
	}

	@Override
	public JobPositionDTO findById(Integer id) {
		return this.mapperJobPosition.map(this.getById(id));
	}

	@Override
	public boolean existsById(Integer id) {
		return jobPositionRepository.existsById(id);
	}

	@Override
	public long count() {
		return jobPositionRepository.count();
	}

	@Override
	public boolean existsByName(String name) {
		return jobPositionRepository.existsByName(name);
	}

	@Override
	public void deleteById(Integer id) {
		jobPositionRepository.deleteById(id);
	}
	
	public JobPosition getById(Integer id) {
		return jobPositionRepository.findById(id).orElseThrow(()-> new ResourceNotFound("JobPosition not found with id: " + id));

	}
	
	
}
