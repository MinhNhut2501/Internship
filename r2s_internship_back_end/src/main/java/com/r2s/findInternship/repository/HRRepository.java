package com.r2s.findInternship.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.HR;


@Repository
public interface HRRepository  extends JpaRepository<HR, Integer> {
	@Query("SELECT hr FROM HR hr WHERE hr.user.username = :username")
	Optional<HR > findByUserName(@Param("username") String username);
	
	@Query("SELECT hr FROM HR hr WHERE hr.user.id = :id")
	HR findByUserId(@Param("id") long id);
	
	
}
