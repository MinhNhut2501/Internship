package com.r2s.findInternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Countries;
@Repository
public interface CountriesRepository extends JpaRepository<Countries, Integer> {
	

}
