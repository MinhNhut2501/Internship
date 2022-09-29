package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.RoleService;
import com.r2s.findInternship.entity.Role;

@RestController
@RequestMapping("api/r2s/admin/role")
@PreAuthorize("hasAuthority('Role_Admin')")
public class RoleAdminController {
	@Autowired
	private RoleService roleService;
	@GetMapping("")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.ok(this.roleService.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.roleService.findById(id));
	}
	@PostMapping("")
	public ResponseEntity<Role> create(@RequestBody Role role)
	{
		return new ResponseEntity<Role>(this.roleService.save(role), HttpStatus.CREATED);
	}
}
