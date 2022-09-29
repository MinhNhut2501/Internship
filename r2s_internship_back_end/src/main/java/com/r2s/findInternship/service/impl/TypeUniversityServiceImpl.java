package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.dto.TypeUniversityDTO;
import com.r2s.findInternship.entity.TypeUniversity;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperTypeUniversity;
import com.r2s.findInternship.repository.TypeUniversityRepository;
import com.r2s.findInternship.service.TypeUniversityService;

@Service
public class TypeUniversityServiceImpl implements TypeUniversityService {
	@Autowired
	private TypeUniversityRepository typeUniversityRepository;
	@Autowired
	private MapperTypeUniversity mapperTypeUniversity;
	@Override
	public TypeUniversityDTO save(TypeUniversity entity) {
		return this.mapperTypeUniversity.map(this.typeUniversityRepository.save(entity));
	}

	@Override
	public List<TypeUniversityDTO> findAll() {
		return typeUniversityRepository.findAll().stream().map(item -> this.mapperTypeUniversity.map(item)).collect(Collectors.toList());
	}

	@Override
	public TypeUniversityDTO findById(Integer id) {
		TypeUniversity entity = this.typeUniversityRepository.findById(id).orElseThrow(() -> new ResourceNotFound("TypeUniversity","id",String.valueOf(id)));
		return this.mapperTypeUniversity.map(entity);
	}

	@Override
	public boolean existsById(Integer id) {
		return typeUniversityRepository.existsById(id);
	}
	
}
