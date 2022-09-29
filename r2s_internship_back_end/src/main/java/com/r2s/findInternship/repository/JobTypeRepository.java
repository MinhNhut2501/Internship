package com.r2s.findInternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r2s.findInternship.entity.JobType;

public interface JobTypeRepository extends JpaRepository<JobType, Integer> {

}
