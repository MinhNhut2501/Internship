package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.MajorDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.entity.Major;

public interface MajorService {

	MajorDTO findById(Integer id);

	PaginationDTO findAll(int no, int limit);

	MajorDTO save(MajorDTO dto);
	
	MajorDTO update(int id, MajorDTO dto);
	
	Major getById(Integer id);

	void deleteById(Integer id);

}
