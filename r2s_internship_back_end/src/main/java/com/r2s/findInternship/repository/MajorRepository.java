package com.r2s.findInternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Major;
@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {
	boolean existsByName(String name);
}
