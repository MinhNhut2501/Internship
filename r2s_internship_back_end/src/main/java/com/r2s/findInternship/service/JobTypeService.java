package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.dto.JobTypeDTO;
import com.r2s.findInternship.entity.JobType;

public interface JobTypeService {
	
	boolean deleteById(Integer id);

	boolean existsById(Integer id);

	JobTypeDTO findById(Integer id);

	List<JobTypeDTO> findAll();
	
	JobTypeDTO update(int id, ApplyListDTO dto);
	
	JobType getById(Integer id);

	JobTypeDTO save(JobTypeDTO entity);

	boolean existsByName(String name);

}
