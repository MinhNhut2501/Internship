package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.DistrictDTO;
import com.r2s.findInternship.entity.District;

public interface DistrictService {

	DistrictDTO findById(Integer id);

	List<DistrictDTO> findAll();

	DistrictDTO save(DistrictDTO dto);
	
	DistrictDTO update(int id, DistrictDTO dto);
	
	District getById(Integer id);

	void deleteById(Integer id);

	List<DistrictDTO> getDistrictByProvinceId(Integer id);
}
