package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.dto.CompanyLocationDTO;
import com.r2s.findInternship.entity.CompanyLocation;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperCompanyLocation;
import com.r2s.findInternship.repository.CompanyLocationRepository;
import com.r2s.findInternship.service.CompanyLocationService;
@Service
public class CompanyLocationServiceImpl implements CompanyLocationService{

	@Autowired
	private CompanyLocationRepository companyLocationRepository;
	@Autowired
	private MapperCompanyLocation mapperCompanyLocation;
	@Autowired
	private MessageSource messageSource;
    public final static Logger logger = LoggerFactory.getLogger("info");

	@Override
	public CompanyLocationDTO findById(Integer id) {
		return this.mapperCompanyLocation.map(this.getById(id));	
	}
	@Override
	public List<CompanyLocationDTO> findAll() {
		return companyLocationRepository.findAll().stream().map(companyLocation -> this.mapperCompanyLocation.map(companyLocation)).collect(Collectors.toList());
		
	}

	@Override
	public CompanyLocationDTO save(CompanyLocationDTO dto) {
		CompanyLocation entity = companyLocationRepository.save(mapperCompanyLocation.map(dto));
        logger.info(String.format("CompanyLocation id %d  save successfully",entity.getId())); 
		return mapperCompanyLocation.map(entity);
	}

	@Override
	public CompanyLocationDTO update(int id, CompanyLocationDTO dto) {
		CompanyLocation entity = companyLocationRepository.save(mapperCompanyLocation.map(dto));
        logger.info(String.format("CompanyLocation id %d  update successfully",entity.getId())); 
		return mapperCompanyLocation.map(entity);
	}

	@Override
	public CompanyLocation getById(Integer id) {
		return companyLocationRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Location","id",String.valueOf(id)));

	}

	@Override
	public boolean deleteById(Integer id) {
		try {
			CompanyLocation entity = this.getById(id);
			companyLocationRepository.delete(entity);
			
		} catch (Exception e) {
			throw new ResourceNotFound("Company Location","id",String.valueOf(id));

		}
		return true;
	}


	@Override
	public void delete(CompanyLocation entity) {
		companyLocationRepository.delete(entity);
        logger.info(String.format("CompanyLocation id %d  delete successfully",entity.getId())); 

	}


	@Override
	public CompanyLocationDTO readJson(String value) {
		CompanyLocationDTO companyLocationDTO = new CompanyLocationDTO();
		try
		{
			ObjectMapper ob = new ObjectMapper();
			companyLocationDTO = ob.readValue(value, CompanyLocationDTO.class);
		}catch (JsonProcessingException e) {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
		}
		return companyLocationDTO;
	}

}
