package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.dto.JobPositionDTO;
import com.r2s.findInternship.service.JobPositionService;

import net.bytebuddy.asm.Advice.This;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/jobPosition")
@PreAuthorize("hasAuthority('Role_Admin')")
public class JobPositionAdminController {
	@Autowired
	private JobPositionService jobPositionService;
	@PostMapping("")//add
	public ResponseEntity<JobPositionDTO> create(@RequestBody JobPositionDTO jobPositionDTO)
	{
		return new ResponseEntity<JobPositionDTO>(jobPositionService.save(jobPositionDTO), HttpStatus.CREATED);
	}
}
