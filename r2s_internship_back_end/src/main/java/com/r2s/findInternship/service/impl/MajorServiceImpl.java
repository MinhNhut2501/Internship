package com.r2s.findInternship.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.service.MajorService;
import com.r2s.findInternship.dto.MajorDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.entity.Major;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperMajor;
import com.r2s.findInternship.repository.MajorRepository;
@Service
public class MajorServiceImpl implements MajorService {
	@Autowired
	private MajorRepository majorRepository;
	@Autowired
	private MapperMajor mapperMajor;

	@Override
	public  MajorDTO save(MajorDTO dto) {
		Major entity = this.mapperMajor.map(dto);
		entity.setCreateDate(LocalDate.now());		
		if(this.existsByName(entity.getName())) throw new InternalServerErrorException(String.format("Major is exists in my system wiht %s, so you cann't add it", entity.getName()));
		entity =  majorRepository.save(entity);
		
		return this.mapperMajor.map(entity);
	}

	/*
	 * @Override public List<MajorDTO> findAll() { return
	 * majorRepository.findAll().stream().map( major -> this.mapper.map(major)
	 * ).collect(Collectors.toList());
	 * 
	 * }
	 */
	
	@Override
	public PaginationDTO findAll(int no, int limit) {
		Pageable pageable = PageRequest.of(no, PaginationDTO.size);
		List<Object> listDtos = majorRepository.findAll(pageable).toList().stream().map(item -> mapperMajor.map(item)).collect(Collectors.toList());
		Page<Major> majorPage = majorRepository.findAll(pageable);
		return new PaginationDTO(listDtos, majorPage.isFirst(), majorPage.isLast(), majorPage.getTotalPages(), majorPage.getTotalElements(), majorPage.getSize(), majorPage.getNumber());
	}
	public Page<Major> findAll(Pageable pageable) {
		return majorRepository.findAll(pageable);
	}

	public List<Major> findAll(Sort sort) {
		return majorRepository.findAll(sort);
	}

	public List<Major> findAllById(Iterable<Integer> ids) {
		return majorRepository.findAllById(ids);
	}

	@Override
	public MajorDTO findById(Integer id) {
		return this.mapperMajor.map(this.getById(id));
	}

	public boolean existsById(Integer id) {
		return majorRepository.existsById(id);
	}

	@Override
	public void deleteById(Integer id) {
		if(this.getById(id)!= null)
			this.majorRepository.deleteById(id);
	}

	public void delete(Major entity) {
		majorRepository.delete(entity);
	}

	public <S extends Major> List<S> findAll(Example<S> example) {
		return majorRepository.findAll(example);
	}

	@Override
	public MajorDTO update(int id, MajorDTO dto) {
		Major entity = this.getById(id);
		entity.setName(dto.getName());
		if(this.existsByName(entity.getName())) throw new InternalServerErrorException(String.format("Major is exists in my system wiht %s, so you cann't add it", entity.getName()));
		return this.mapperMajor.map(this.majorRepository.save(entity));
	}

	@Override
	public Major getById(Integer id) {

		return majorRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Major","id",String.valueOf(id)));
	}

	public boolean existsByName(String name) {
		return majorRepository.existsByName(name);
	}
	
	

}
