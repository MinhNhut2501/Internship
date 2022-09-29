package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.service.ProvinceService;
import com.r2s.findInternship.dto.ProvinceDTO;
import com.r2s.findInternship.entity.Province;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperProvince;
import com.r2s.findInternship.repository.ProvinceRepository;

@Service
public class ProvinceServiceImpl implements ProvinceService {
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private MapperProvince mapper;
	
	@Override
	public ProvinceDTO findById(Integer id) {
		return this.mapper.map(this.getById(id));
	}

	@Override
	public List<ProvinceDTO> findAll() {
		return provinceRepository.findAll(Sort.by(Order.by("name"))).stream().map(District -> this.mapper.map(District)).collect(Collectors.toList());
	}

	@Override
	public ProvinceDTO update(int id, ProvinceDTO dto) {
		Province entity = this.getById(id);
		entity = this.mapper.map(dto);
		provinceRepository.save(entity);
		return mapper.map(entity);
	
	}

	@Override
	public Province getById(Integer id) {
		return provinceRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Province not found with id: " + id));

	}

	@Override
	public void deleteById(Integer id) {
		provinceRepository.deleteById(id);

		
	}
	
	public void delete(Province entity) {
		provinceRepository.delete(entity);
	}

	@Override
	public ProvinceDTO save(ProvinceDTO dto) {
		Province entity = this.mapper.map(dto);
		entity =  provinceRepository.save(entity);	
		return mapper.map(entity);
	}

	@Override
	public List<ProvinceDTO> getProvinceByCountryId(Integer id) {
		return provinceRepository.getProvinceByCountryId(id).stream().map(District -> this.mapper.map(District)).collect(Collectors.toList());
	}
	

}
