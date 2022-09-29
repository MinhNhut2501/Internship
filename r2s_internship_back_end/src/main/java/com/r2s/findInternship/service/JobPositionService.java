package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.JobPositionDTO;

public interface JobPositionService {

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	JobPositionDTO findById(Integer id);

	List<JobPositionDTO> findAll();

	JobPositionDTO  save(JobPositionDTO entity);

	boolean existsByName(String name);

}
