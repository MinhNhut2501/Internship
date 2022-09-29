package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.common.ERole;
import com.r2s.findInternship.entity.Role;

public interface RoleService {

	boolean existsByName(String name);

	Role getById(Integer id);

	Role findById(Integer id);

	List<Role> findAll();

	<S extends Role> S save(S entity);
	
	Role findByName(String name);

	Role findByName(ERole role);

}
