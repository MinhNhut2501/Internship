package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.StatusDTO;
import com.r2s.findInternship.service.StatusService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/status")
@PreAuthorize("hasAuthority('Role_Admin')")
public class StatusAdminController {
	@Autowired
	private StatusService statusService;
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		return ResponseEntity.ok(this.statusService.findById(id));
	}
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(this.statusService.findAll());
	}
	
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody StatusDTO statusDTO) {
		return new ResponseEntity<StatusDTO>(this.statusService.save(statusDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody StatusDTO statusDTO, @PathVariable int id) {
		return ResponseEntity.ok(this.statusService.update(id, statusDTO));
	}
	
}
