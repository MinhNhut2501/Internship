package com.r2s.findInternship.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r2s.findInternship.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{
	Optional<Status> findByName(String name);
}
