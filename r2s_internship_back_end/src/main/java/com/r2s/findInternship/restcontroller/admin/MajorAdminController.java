package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.dto.MajorDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.service.MajorService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/major")
public class MajorAdminController {
	@Autowired
	private MajorService majorService;
	
	@GetMapping("")
	public ResponseEntity<PaginationDTO> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.majorService.findAll(no, limit));
	}

	@PreAuthorize("hasAuthority('Role_Admin')")
	@GetMapping("/{id}")
	public ResponseEntity<MajorDTO> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.majorService.findById(id));
	}
	@PreAuthorize("hasAuthority('Role_Admin')")
	@PostMapping("")
	public ResponseEntity<MajorDTO> create(@RequestBody MajorDTO dto)
	{	
		return new ResponseEntity<MajorDTO>(this.majorService.save(dto), HttpStatus.CREATED);
	}
	@PreAuthorize("hasAuthority('Role_Admin')")
	@PutMapping("/{id}")
	public ResponseEntity<MajorDTO> update(@PathVariable int id, @RequestBody MajorDTO dto)
	{
		return ResponseEntity.ok(this.majorService.update(id, dto));
	}
	@PreAuthorize("hasAuthority('Role_Admin')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id)
	{
		this.majorService.deleteById(id);
		return ResponseEntity.ok("DELETED");
	}
}
