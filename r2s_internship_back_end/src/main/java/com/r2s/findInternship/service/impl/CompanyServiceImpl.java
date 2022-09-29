package com.r2s.findInternship.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.common.FileUpload;
import com.r2s.findInternship.dto.CompanyDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.entity.Company;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperCompany;
import com.r2s.findInternship.mapstructmapper.MapperStatus;
import com.r2s.findInternship.repository.CompanyRepository;
import com.r2s.findInternship.repository.StatusRepository;
import com.r2s.findInternship.service.CompanyService;
import com.r2s.findInternship.util.UpdateFile;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private MapperCompany mapper;
	@Autowired
	private MapperStatus mapperStatus;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UpdateFile updateFile;
	
    public final static Logger logger = LoggerFactory.getLogger("info");

	//Find by ID ---> Get by ID
	@Override
	public CompanyDTO findById(Integer id) {
		return this.mapper.map(this.getById(id));
	}
	//Find all                       
	@Override
	public PaginationDTO findAllPaging(int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);// Page: 0 and Member: 10
		List<Object> companies = this.companyRepository.findAll(pageable).toList().stream()
				.map(item -> this.mapper.map(item)).collect(Collectors.toList());
		Page<Company> pageCompany = this.companyRepository.findAll(pageable);
		return new PaginationDTO(companies, pageCompany.isFirst(), pageCompany.isLast(),
				pageCompany.getTotalPages(), pageCompany.getTotalElements(), pageCompany.getSize(), pageCompany.getNumber());
	}
	
	// Save 
	@Override
	public CompanyDTO save(CompanyDTO dto) {	
		dto.setDate(LocalDate.now());
		Company entity = this.mapper.map(dto);
		if(dto.getStatus()!= null) {
			dto.setStatus(mapperStatus.map(statusRepository.getById(entity.getStatus().getId())));
		}
		entity.setStatus(statusRepository.getById(1));
		entity = companyRepository.save(entity);
        logger.info(String.format("Company %s save successfully",entity.getName())); 
		return this.mapper.map(entity);
	}
	
	//Update
	@Override
	public CompanyDTO update(int id, CompanyDTO dto) {
		Company entity = this.getById(id);
		dto.setId(id);
		if(dto.getStatus()== null) {
			dto.setStatus(mapperStatus.map(entity.getStatus()));
		}else {
			dto.setStatus(mapperStatus.map(statusRepository.getById(dto.getStatus().getId())));
		}
		if(dto.getDate()== null) {
			dto.setDate(entity.getDate());

		}
		if(dto.getFileLogo()== null) {
			dto.setLogo(entity.getLogo());

		}
		entity = mapper.map(dto);
		entity = companyRepository.save(entity);
        logger.info(String.format("Company %s update successfully",entity.getName())); 
		return mapper.map(entity);
	
	}
	
	// Get by ID
	@Override
	public Company getById(Integer id) {
		return companyRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Company","id",String.valueOf(id)));
	}
	
	
	//Bắt lỗi trùng Email, Name cho update
	@Override
	public Map<String, String>  checkCompany(int id,CompanyDTO entity) {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		for (Company company : companyRepository.findAll()) {
			if(id != company.getId()) {
				if(entity.getName().equals(company.getName()))
				{
					errors.put("Name", messageSource.getMessage("error.companyNameAlready", null, null));
				}
				if(entity.getEmail().equals(company.getEmail())) {
					errors.put("Email", messageSource.getMessage("error.companyEmailAlready", null, null));
					}
				if(entity.getWebsite().equals(company.getWebsite())) {
					errors.put("Website", messageSource.getMessage("error.companyWebsiteAlready", null, null));
					}
			}
				
		}
		if(errors.size()>0)
			throw new InternalServerErrorException(errors);
		
		return errors;
			
	}
	
	//Bắt lỗi trùng Email, Name cho create
	@Override
	public Map<String, String> checkCompany(CompanyDTO entity) {
		Map<String, String> errors = new HashMap<String, String>();
		
		for (Company company : companyRepository.findAll()) {
			
				if(entity.getName().equals(company.getName()))
				{
					errors.put("Name", messageSource.getMessage("error.companyNameAlready", null, null));
				}
				if(entity.getEmail().equals(company.getEmail())) {
					errors.put("Email", messageSource.getMessage("error.companyEmailAlready", null, null));
					}
				if(entity.getWebsite().equals(company.getWebsite())) {
					errors.put("Website", messageSource.getMessage("error.companyWebsiteAlready", null, null));
			}
				
		}
		if(errors.size()>0)
			throw new InternalServerErrorException(errors);
		
		return errors;
	}

	// Delete by ID --> Delete
	@Override
	public boolean deleteById(Integer id) {
		try {
			Company entity = this.getById(id);
			entity.setStatus(statusRepository.getById(4));
			companyRepository.save(entity);
			
		} catch (Exception e) {
			throw new ResourceNotFound("Company","id",String.valueOf(id));

		}
		return true;
					
	}
	
	// Delete 
	public void delete(Company entity) {
		companyRepository.delete(entity);
        logger.info(String.format("Company %s delete successfully",entity.getName())); 
	}
	
	// Find by name
	@Override
	public PaginationDTO findByNameContainingPaging(String name, int no, int limit) {
		
		if (name.isEmpty()) return this.findAllPaging(no,limit);
		List<Object> companies = this.companyRepository.findByNameContaining(name).stream()
				.map(item -> this.mapper.map(item)).collect(Collectors.toList());
			
		PaginationDTO pageUserDTO = customPagination(companies, no, limit);
		return pageUserDTO;
	}
	@Override
	public PaginationDTO customPagination(List<Object> list,int page, int limmit) {
         List<Object> companiesInAPage = new ArrayList<Object>();
		if(list.size()==0) {
			return null;
		}
		int totalPages = 0;
		int totalItems = list.size();
		boolean first = false ;
		boolean last = false ;
		totalPages = totalItems/limmit;
		if(totalItems%limmit>0) {
			totalPages++;
		}
		if(page == 0) {
			first = true;
		}
		if((page+1)/totalPages >= 1) {
			last = true;
		}
		if(page>=0) {
			int n = page*limmit;
			for (int i = n; i < n+limmit & i < list.size(); i++) {
				companiesInAPage.add(list.get(i));
			}
		}
		PaginationDTO pageUserDTO = new PaginationDTO(companiesInAPage, first, last,totalPages ,totalItems, limmit, page);
		return pageUserDTO;
	}

	@Override
	public CompanyDTO readJson(String value, MultipartFile fileLogo) {
		CompanyDTO companyDTO = new CompanyDTO();
		try
		{
			ObjectMapper ob = new ObjectMapper();
			companyDTO = ob.readValue(value, CompanyDTO.class);
		}catch (JsonProcessingException e) {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.CVNull", null, null));
		}
		if(fileLogo != null)// Set file Logo
			companyDTO.setFileLogo(fileLogo); 
		
		if (companyDTO.getFileLogo() != null) // Set path of file Logo
			{
			FileUpload file = new FileUpload();
			file.setFile(fileLogo);  
			this.updateFile.update(file);
			companyDTO.setLogo(file.getOutput());
	        logger.info(String.format("Company %s update logo %s successfully",companyDTO.getName(), companyDTO.getLogo() )); 

		}
		return companyDTO;
	}

	@Override
	public void flush() {		
	}
	@Override
	public Long statisticsNewCompany() {
		return companyRepository.statisticsNewCompany();
	}
	public Long getCountCompanyByDate(String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fromDate  = LocalDate.parse(from, formatter);
		LocalDate toDate  = LocalDate.parse(to, formatter);
		return companyRepository.getCountCompanyByDate(fromDate,toDate);
	}
	@Override
	public List<Object[]> statisticsByStatus() {
		return companyRepository.statisticsByStatus();
	}
	public long count() {
		return companyRepository.count();
	}
	
	@Override
	public Long getCountByDate(String from, String to)
	{
		if(from!= "")
		{
			return getCountCompanyByDate(from, to);
		}
		else 
		{
			return count();
		}
	}
	// Get company by Job id
	@Override
	public CompanyDTO getByJobId(Integer id) {
		if(companyRepository.getCompanyByJobId(id)==null) {
			throw new ResourceNotFound("Company","id of Job",String.valueOf(id));
		}
		return mapper.map(companyRepository.getCompanyByJobId(id));
	}
	
	//Find all                       
	@Override
	public PaginationDTO findAllActivePaging(int no, int limit) {
		List<Object> companies = this.companyRepository.getCompaniesActive().stream()
				.map(item -> this.mapper.map(item)).collect(Collectors.toList());
			
		PaginationDTO pageDTO = customPagination(companies, no, limit);
		if(pageDTO==null) {
			throw new ResourceNotFound("Company","query",null);
		}
		return pageDTO;
	}


	
	

}
