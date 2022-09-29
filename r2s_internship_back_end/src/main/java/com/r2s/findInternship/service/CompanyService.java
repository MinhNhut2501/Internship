package com.r2s.findInternship.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import com.r2s.findInternship.dto.CompanyDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.entity.Company;

public interface CompanyService {
	PaginationDTO findByNameContainingPaging(String name, int no, int limit);

	PaginationDTO findAllPaging(int no, int limit);

	PaginationDTO findAllActivePaging(int no, int limit);

	CompanyDTO findById(Integer id);
		
	CompanyDTO  getByJobId(Integer id);

	CompanyDTO save(CompanyDTO dto);
	
	CompanyDTO update(int id, CompanyDTO dto);
	
	Company getById(Integer id);

	boolean deleteById(Integer id);

	Map<String, String> checkCompany(int id, CompanyDTO entity);
	
	Map<String, String> checkCompany(CompanyDTO entity);

	
	CompanyDTO readJson(String value, MultipartFile fileLogo);
	
	void flush();

	Long getCountByDate(String from, String to);

	List<Object[]> statisticsByStatus();

	Long statisticsNewCompany();
	
	PaginationDTO customPagination(List<Object> list,int page, int limmit);

}
