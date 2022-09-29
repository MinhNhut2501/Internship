package com.r2s.findInternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.TypeUniversity;
@Repository
public interface TypeUniversityRepository extends JpaRepository<TypeUniversity, Integer> {

}
