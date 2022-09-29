package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.dto.JobTypeDTO;
import com.r2s.findInternship.entity.JobType;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperJobType;
import com.r2s.findInternship.repository.JobTypeRepository;
import com.r2s.findInternship.service.JobTypeService;
@Service
public class JobTypeServiceImpl implements JobTypeService{

	@Autowired
	private JobTypeRepository jobTypeRepository;
	@Autowired
	private MapperJobType mapperJobType;
	
	@Override
	public boolean deleteById(Integer id) {
		jobTypeRepository.deleteById(id);
		return true;

	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JobTypeDTO findById(Integer id) {
		return this.mapperJobType.map(this.getById(id));

	}

	@Override
	public List<JobTypeDTO> findAll() {
		return jobTypeRepository.findAll(Sort.by(Order.by("name"))).stream().map(JobType -> this.mapperJobType.map(JobType)).collect(Collectors.toList());

	}

	@Override
	public JobTypeDTO update(int id, ApplyListDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobType getById(Integer id) {
		return jobTypeRepository.findById(id).orElseThrow(()-> new ResourceNotFound("JobType not found with id: " + id));

	}

	@Override
	public JobTypeDTO save(JobTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
