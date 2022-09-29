package com.r2s.findInternship.restcontroller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.dto.JobPositionDTO;
import com.r2s.findInternship.entity.JobPosition;
import com.r2s.findInternship.mapstructmapper.MapperJobPosition;
import com.r2s.findInternship.service.JobPositionService;

@CrossOrigin(maxAge = 3600,origins = "*")
@RestController
@RequestMapping("api/r2s/JobPosition")
public class JobPositionGeneralController {
	@Autowired
	private JobPositionService jobPositionService;
	@Autowired
	private MapperJobPosition mapperJobPosition;
	@GetMapping("")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.ok(this.jobPositionService.findAll());
	}
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody JobPosition jobPosition)
	{
		return new ResponseEntity<JobPositionDTO>(this.jobPositionService.save(mapperJobPosition.map(jobPosition)), HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.jobPositionService.findById(id));
	}
}
