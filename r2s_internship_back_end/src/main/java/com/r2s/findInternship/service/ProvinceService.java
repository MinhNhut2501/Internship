package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.ProvinceDTO;
import com.r2s.findInternship.entity.Province;

public interface ProvinceService {
	
	ProvinceDTO findById(Integer id);

	List<ProvinceDTO> findAll();

	ProvinceDTO save(ProvinceDTO dto);
	
	ProvinceDTO update(int id,ProvinceDTO dto);
	
	Province getById(Integer id);

	void deleteById(Integer id);
	
	List<ProvinceDTO> getProvinceByCountryId(Integer id); 

}
