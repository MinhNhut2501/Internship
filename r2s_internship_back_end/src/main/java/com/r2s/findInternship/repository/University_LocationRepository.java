package com.r2s.findInternship.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.r2s.findInternship.entity.University_Location;

public interface University_LocationRepository extends JpaRepository<University_Location, Integer> {
	@Query("SELECT u FROM University_Location u WHERE u.university.id = :id")
	Set<University_Location> findByUniversityId(@Param("id") int id);
}
