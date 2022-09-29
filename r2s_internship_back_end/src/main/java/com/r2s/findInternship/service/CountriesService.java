package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.CountriesDTO;
import com.r2s.findInternship.entity.Countries;

public interface CountriesService {

	void deleteById(Integer id);

	long count();

	Countries getById(Integer id);
	
	boolean existsById(Integer id);

	Countries findById(Integer id);

	List<CountriesDTO> findAll();

	CountriesDTO save(CountriesDTO dto);
	
	CountriesDTO update(int id, CountriesDTO dto);


}
