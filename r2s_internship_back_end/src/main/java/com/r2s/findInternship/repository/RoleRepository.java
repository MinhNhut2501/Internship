package com.r2s.findInternship.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	boolean existsByName(String name);
	Optional<Role> findByName(String name);
}
