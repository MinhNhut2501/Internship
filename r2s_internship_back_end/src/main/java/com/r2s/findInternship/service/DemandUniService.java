package com.r2s.findInternship.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.DemandUniDTO;
import com.r2s.findInternship.dto.DemandUniShowDTO;
import com.r2s.findInternship.dto.PaginationDTO;


public interface DemandUniService {
	PaginationDTO findAllPagingLatest(int no, int limit);

	PaginationDTO searchByNamePagingLatest(String name, int no, int limit);

	PaginationDTO findByUniversityIdPaging(int id, int no, int limit);

	DemandUniShowDTO findById(int id);

	DemandUniDTO readJson(String content, MultipartFile file);

	List<DemandUniShowDTO> findAll();

	DemandUniShowDTO save(DemandUniDTO dto);
	
	DemandUniShowDTO update(DemandUniDTO dto, int id);

	void delete(int id);

}
