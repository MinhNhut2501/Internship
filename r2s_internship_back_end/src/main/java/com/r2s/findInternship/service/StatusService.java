package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.common.Estatus;
import com.r2s.findInternship.dto.StatusDTO;
import com.r2s.findInternship.entity.Status;

public interface StatusService {
	
	boolean existsById(int id);
	
	StatusDTO save(StatusDTO entity);
	
	List<StatusDTO> findAll();
	
	StatusDTO findById(Integer id);
	
	Status findByName(Estatus e);
	
	void deleteById(Integer id);
	
	StatusDTO update(int id, StatusDTO status);
}
