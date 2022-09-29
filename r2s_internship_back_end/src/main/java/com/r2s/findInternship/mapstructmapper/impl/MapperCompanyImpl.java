package com.r2s.findInternship.mapstructmapper.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.r2s.findInternship.dto.CompanyDTO;
import com.r2s.findInternship.entity.Company;
import com.r2s.findInternship.mapstructmapper.MapperCompany;
import com.r2s.findInternship.mapstructmapper.MapperLocation;
import com.r2s.findInternship.mapstructmapper.MapperStatus;
@Component
public class MapperCompanyImpl implements MapperCompany {
	@Autowired
	MapperLocation mapperLocation;
	@Autowired
	MapperStatus mapperStatus;
	@Override
	public Company map(CompanyDTO dto) {
		if(dto == null)return null;
		Company company = new Company();
		company.setId(dto.getId());
		company.setName(dto.getName());
		company.setLogo(dto.getLogo());
		company.setDescription(dto.getDescription());
		company.setWebsite(dto.getWebsite());
		company.setEmail(dto.getEmail());
		company.setPhone(dto.getPhone());
		company.setDate(dto.getDate());
		company.setTax(dto.getTax());
		company.setStatus(mapperStatus.map(dto.getStatus()));

		return company;
	}

	@Override
	public CompanyDTO map(Company entity) {
		if(entity == null)return null;
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(entity.getId());
		companyDTO.setName(entity.getName());
		companyDTO.setLogo(entity.getLogo());
		companyDTO.setDescription(entity.getDescription());
		companyDTO.setWebsite(entity.getWebsite());
		companyDTO.setEmail(entity.getEmail());
		companyDTO.setPhone(entity.getPhone());
		companyDTO.setDate(entity.getDate());
		companyDTO.setStatus(this.mapperStatus.map(entity.getStatus()));
		companyDTO.setTax(entity.getTax());
		return companyDTO;
	}

}
