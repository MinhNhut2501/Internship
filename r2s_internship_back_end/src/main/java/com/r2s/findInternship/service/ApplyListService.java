package com.r2s.findInternship.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.entity.ApplyList;

public interface ApplyListService {
	PaginationDTO findAllPaging(int no, int limit);

	PaginationDTO findByCandidateIdPaging(int candidateID, int no, int limit);

	PaginationDTO findByJobIdPaging(int jobId, int no, int limit);

	PaginationDTO findByUsernamePaging(String username, int no, int limit);

	ApplyListDTO findById(Integer id);
	
	boolean checkApply(int jobId, int candidateId);

	ApplyListDTO save(ApplyListDTO dto);
	
	ApplyListDTO update(int id, ApplyListDTO dto);
	
	ApplyList getById(Integer id);
	
	boolean deleteById(Integer id);
	
	ApplyListDTO readJson(String value, MultipartFile fileLogo);
	
	PaginationDTO customPagination(List<Object> list,int page, int limmit);

	
}
