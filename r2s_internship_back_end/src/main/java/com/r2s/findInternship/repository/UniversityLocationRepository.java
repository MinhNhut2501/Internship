package com.r2s.findInternship.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.r2s.findInternship.entity.UniversityLocation;

public interface UniversityLocationRepository extends JpaRepository<UniversityLocation, Integer> {
	@Query("SELECT u FROM UniversityLocation u WHERE u.university.id = :id")
	List<UniversityLocation> findByUniversityId(@Param("id") int id);
	@Query("SELECT u FROM UniversityLocation u WHERE u.location.id = :id")
	List<UniversityLocation> findByLocationId(@Param("id") int id);

	@Query("SELECT u FROM UniversityLocation u WHERE u.university.id = :uid AND u.location.id = :lid")
	Optional<UniversityLocation> findByUniversityIdAndLocationId(@Param("uid") int universityId, @Param("lid") int locationId);
}
