package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.mapstructmapper.impl.MapperDistrictImpl;
import com.r2s.findInternship.service.DistrictService;
import com.r2s.findInternship.dto.DistrictDTO;
import com.r2s.findInternship.entity.District;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperDistrict;
import com.r2s.findInternship.repository.DistrictRepository;
@Service
public class DistrictServiceImpl implements DistrictService {
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private MapperDistrict mapper;
	
	@Override
	public DistrictDTO findById(Integer id) {
		return this.mapper.map(this.getById(id));
	}

	@Override
	public List<DistrictDTO> findAll() {
		return districtRepository.findAll(Sort.by(Order.by("name"))).stream().map(District -> this.mapper.map(District)).collect(Collectors.toList());
	}

	@Override
	public DistrictDTO save(DistrictDTO dto) {
		District entity = this.mapper.map(dto);
		entity =  districtRepository.save(entity);	
		return mapper.map(entity);
	}

	@Override
	public DistrictDTO update(int id, DistrictDTO dto) {
		District entity = this.getById(id);
		entity = new MapperDistrictImpl().map(dto);
		districtRepository.save(entity);
		return mapper.map(entity);
	
	}

	@Override
	public District getById(Integer id) {
		return districtRepository.findById(id).orElseThrow(()-> new ResourceNotFound("District","id",String.valueOf(id)));

	}

	@Override
	public void deleteById(Integer id) {
		districtRepository.deleteById(id);

		
	}
	
	public void delete(District entity) {
		districtRepository.delete(entity);
	}

	@Override
	public List<DistrictDTO> getDistrictByProvinceId(Integer id) {
		return districtRepository.getDistrictByProvinceId(id).stream().map(District -> this.mapper.map(District)).collect(Collectors.toList());

	}
	
	
}
