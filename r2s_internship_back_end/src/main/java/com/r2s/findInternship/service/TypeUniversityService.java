package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.TypeUniversityDTO;
import com.r2s.findInternship.entity.TypeUniversity;

public interface TypeUniversityService {

	boolean existsById(Integer id);

	TypeUniversityDTO findById(Integer id);

	List<TypeUniversityDTO> findAll();

	TypeUniversityDTO save(TypeUniversity entity);

}
