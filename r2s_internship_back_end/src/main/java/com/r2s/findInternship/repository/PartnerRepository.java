package com.r2s.findInternship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Partner;
@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
	@Query("SELECT p FROM Partner p WHERE p.user.id = :id")
	Optional<Partner> findByUsername(@Param(value = "id") long username);
	@Query("SELECT p FROM Partner p WHERE p.university.id = :id")
	List<Partner> findByUniversityId(@Param("id") int id);

	@Query("SELECT p FROM Partner p WHERE p.university.id = :id")
	Page<Partner> findByUniversityId(@Param("id") int id, Pageable pageable);
	
	@Query("SELECT a FROM Partner a WHERE a.id = :id")
	Partner findByPartnerId(@Param("id") int id);
}
