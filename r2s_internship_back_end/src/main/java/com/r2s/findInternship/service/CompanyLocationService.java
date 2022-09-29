package com.r2s.findInternship.service;

import java.util.List;
import com.r2s.findInternship.dto.CompanyLocationDTO;
import com.r2s.findInternship.entity.CompanyLocation;

public interface CompanyLocationService {

	
    CompanyLocationDTO findById(Integer id);
	
	List<CompanyLocationDTO> findAll();

	CompanyLocationDTO save(CompanyLocationDTO dto);
	
	CompanyLocationDTO update(int id, CompanyLocationDTO dto);
	
	CompanyLocation getById(Integer id);

	boolean deleteById(Integer id);
	
	void delete(CompanyLocation entity);
	
	CompanyLocationDTO readJson(String value);

}
