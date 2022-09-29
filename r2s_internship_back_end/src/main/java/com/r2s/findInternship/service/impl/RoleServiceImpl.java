package com.r2s.findInternship.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.common.ERole;
import com.r2s.findInternship.entity.Role;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.repository.RoleRepository;
import com.r2s.findInternship.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public <S extends Role> S save(S entity) {
		if(this.existsByName(entity.getName())) throw new InternalServerErrorException(String.format("Role is exists in my system wiht %s, so you cann't add it", entity.getName()));
		return roleRepository.save(entity);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(Integer id) {
		return roleRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Role","id",String.valueOf(id)));
	}
	@Override
	public Role getById(Integer id) {
		return roleRepository.getById(id);
	}
	@Override
	public boolean existsByName(String name) {
		return roleRepository.existsByName(name);
	}
	@Override
	public Role findByName(String name) {
		return this.roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFound("Role","name",name));
	}
	@Override
	public Role findByName(ERole role)
	{
		switch (role) {
		case Admin: return this.findById(2);
		case Cadidate: return this.findById(3);
		case HR: return this.findById(1);
		case Partner:return this.findById(4);
		default: return null;
		}
	}
}
