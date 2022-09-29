package com.r2s.findInternship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Province;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
	@Query("SELECT p FROM  Province p WHERE p.country.id = :id")
	List<Province> getProvinceByCountryId(@Param("id") int id);
}
