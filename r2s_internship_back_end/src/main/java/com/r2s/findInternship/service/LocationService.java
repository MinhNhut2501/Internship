package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.LocationDTO;
import com.r2s.findInternship.entity.Location;
public interface LocationService {

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	LocationDTO findById(Integer id);

	List<LocationDTO> findAll();

	LocationDTO save(LocationDTO dto);
	
	LocationDTO update(int id, LocationDTO dto);
	
	Location getById(Integer id);
	
	List<LocationDTO> getLocationByDistrictId(Integer id);
	
	List<LocationDTO> getLocationByCompanyId(Integer id);
	
	LocationDTO readJson(String value);
	
	Location save(Location entity);
	

}
