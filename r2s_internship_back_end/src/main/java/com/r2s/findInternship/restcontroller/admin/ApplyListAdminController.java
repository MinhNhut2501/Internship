package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.service.ApplyListService;

import net.bytebuddy.asm.Advice.This;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/applylist")
@PreAuthorize("hasAuthority('Role_Admin')")
public class ApplyListAdminController {
	@Autowired
	private ApplyListService  applyListService ;
	
	//Get  all 
	@GetMapping("")
	public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.applyListService.findAllPaging(no, limit));
	}
	
	// Save 
	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@RequestPart("apply") String apply,
			@RequestPart(name = "fileCV", required = false) MultipartFile fileCV)
	{	
		ApplyListDTO applyListDTO = applyListService.readJson(apply, fileCV);
		return new ResponseEntity<ApplyListDTO>(this.applyListService.save(applyListDTO), HttpStatus.CREATED);
	}
  
	// Find by id
	@GetMapping("/{id}")
	public ResponseEntity<ApplyListDTO> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.applyListService.findById(id));
	}
	
	
	// Get by job id
	@GetMapping("/job/{id}")
	public ResponseEntity<?> getApplyListByJobId(@PathVariable("id") int id, @RequestParam("no") int no, @RequestParam("limit") int limit){
		return ResponseEntity.ok(this.applyListService.findByJobIdPaging(id, no, limit));
	}

	// Get by Candidate id
	@GetMapping("/candidate/{id}")
	public ResponseEntity<?> getApplyListByCandidateId(@PathVariable int id, @RequestParam("no") int no, @RequestParam("limit") int limit) {
		return ResponseEntity.ok(this.applyListService.findByCandidateIdPaging(id, no, limit));
	}

	@GetMapping("/candidate/user/{username}")
	public ResponseEntity<?> getApplyListByUsername(@PathVariable("username") String username, @RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.applyListService.findByUsernamePaging(username, no, limit));
	}

	
	//Update by id
	@PutMapping("/{id}")
	public ResponseEntity<ApplyListDTO> update(@PathVariable int id, @RequestBody ApplyListDTO dto)
	{
		
		return ResponseEntity.ok(this.applyListService.update(id, dto));
	}
	
	// Delete by id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete( @PathVariable int id)
	{
	
		this.applyListService.deleteById(id);
		return ResponseEntity.ok("DELETED");
	}
	
}
