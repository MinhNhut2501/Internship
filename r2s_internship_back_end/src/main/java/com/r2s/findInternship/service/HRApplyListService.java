package com.r2s.findInternship.service;

import java.util.List;


import com.r2s.findInternship.dto.HRApplyListDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.HRApplyList;

public interface HRApplyListService {
	
	HRApplyListDTO findById(Integer id);
	
	List<HRApplyListDTO> findAll();

	PaginationDTO findAllPagingLatest(int no, int limit);

	PaginationDTO findAllActivePaging(int no, int limit);

	HRApplyListDTO save(HRApplyListDTO dto);
	
	HRApplyListDTO update(int id, HRApplyListDTO dto);
	
	HRApplyList getById(Integer id);

	boolean deleteById(Integer id);

	ResponeMessage delete(int id);

}
