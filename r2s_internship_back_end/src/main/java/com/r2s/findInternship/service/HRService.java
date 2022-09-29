package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.PaginationDTO;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.HRDTO;
import com.r2s.findInternship.dto.show.HRDTOShow;
import com.r2s.findInternship.entity.HR;



public interface HRService {
	
	
	HRDTO findById(Integer id);
	
	HRDTOShow findByUserId(long id);
	
	HRDTOShow findByIdToShow(Integer id);

	List<HRDTOShow> findAll();

	PaginationDTO findAllPagination(int no, int limit);

	HRDTO save(HRDTO dto);
	
	HRDTOShow update(int id, HRDTO dto);
	
	HR getById(Integer id);

	void deleteById(Integer id);
	
	HRDTO readJson(String value, MultipartFile fileAvt);


}
