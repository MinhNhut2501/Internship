package com.r2s.findInternship.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.mapstructmapper.impl.MapperCountriesImpl;
import com.r2s.findInternship.service.CountriesService;
import com.r2s.findInternship.dto.CountriesDTO;
import com.r2s.findInternship.entity.Countries;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperCountries;
import com.r2s.findInternship.repository.CountriesRepository;
@Service
public class CountriesServiceImpl implements CountriesService {
	@Autowired
	private CountriesRepository countriesRepository;
	@Autowired
	private MapperCountries mapperCountries;
	@Autowired
	private MessageSource messageSource;

	@Override
	public List<CountriesDTO> findAll() {
		return countriesRepository.findAll(Sort.by(Order.by("name"))).stream().map(item -> this.mapperCountries.map(item)).collect(Collectors.toList());
	}

	@Override
	public Countries findById(Integer id) {
		Countries c = this.countriesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Countries not found with id: " + id));
		return c;
	}

	@Override
	public boolean existsById(Integer id) {
		return countriesRepository.existsById(id);
	}

	@Override
	public long count() {
		return countriesRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		countriesRepository.deleteById(id);
	}

	@Override
	public CountriesDTO save(CountriesDTO dto) {
		Countries entity = this.mapperCountries.map(dto);
		entity.setCreateDate(LocalDate.now());
		entity =  countriesRepository.save(entity);	
		return this.mapperCountries.map(entity);
	}

	@Override
	public CountriesDTO update(int id, CountriesDTO dto) {
		if(getById(id) != null){
			throw new InternalServerErrorException(messageSource.getMessage("error.countryNotFound", null, null));
		}
		Countries entity = getById(id);
		entity = new MapperCountriesImpl().map(dto);
		countriesRepository.save(entity);
		return new MapperCountriesImpl().map(entity);	
		
	}
	
	@Override
	public Countries getById(Integer id) {
		return countriesRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Country not found with id: " + id));

	}
	public void delete(Countries entity) {
		countriesRepository.delete(entity);
	}
}
