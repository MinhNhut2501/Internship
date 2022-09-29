package com.r2s.findInternship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.District;
@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
	@Query("SELECT d FROM  District d WHERE d.province.id = :id")
	List<District> getDistrictByProvinceId(@Param("id") int id);
}
