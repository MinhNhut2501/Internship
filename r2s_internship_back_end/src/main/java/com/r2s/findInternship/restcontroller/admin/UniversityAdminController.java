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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.UniversityCreateDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.service.UniversityService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/university")
@PreAuthorize("hasAuthority('Role_Admin')")
public class UniversityAdminController {

	@Autowired
	private UniversityService universityService;

	// ADD
	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> createAD(@RequestPart("university") String university,
			@RequestPart(name = "logo", required = false) MultipartFile file) {
		UniversityCreateDTO dto = this.universityService.readJson(university, "", null, file);
		return new ResponseEntity<UniversityDTO>(this.universityService.save(dto), HttpStatus.CREATED);
	}

	// UPDATE
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> update(@PathVariable int id,
			@RequestPart(name = "university", required = false) String university,
			@RequestPart(name = "file", required = false) MultipartFile file) {
		UniversityCreateDTO universityDTO = this.universityService.readJson(university, "", null, file);
		return ResponseEntity.ok(this.universityService.update(universityDTO, id));
	}
	//Disable
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.universityService.deleteById(id));
	}
	//Recover University
	@GetMapping("/re/{id}")
	public ResponseEntity<?> reById(@PathVariable int id)
	{
		this.universityService.recover(id);
		return ResponseEntity.ok("RECOVER IS SUCCESSFULL");
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.universityService.findById(id));
	}
}
