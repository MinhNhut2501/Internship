package com.r2s.findInternship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.r2s.findInternship.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	@Query("SELECT l FROM  Location l WHERE l.district.id = :id")
	List<Location> getLocationByDistrictId(@Param("id") int id);
	
	
	@Query("SELECT l FROM  Location l  WHERE l.id in (select c.location.id from CompanyLocation c where c.company.id =:id)" )
	List<Location> getLocationByCompanyId(@Param("id") int id);
	


}
