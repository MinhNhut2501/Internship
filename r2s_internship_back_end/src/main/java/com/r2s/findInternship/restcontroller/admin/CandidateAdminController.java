package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.service.CandidateService;

@CrossOrigin(maxAge = 3600, origins = "*")
@RestController
@RequestMapping("api/r2s/admin/candidate")
@PreAuthorize("hasAuthority('Role_Admin')")
public class CandidateAdminController {
	@Autowired
	private CandidateService studentService;

	// Get all student
	@GetMapping("")
	public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit) {
		return ResponseEntity.ok(this.studentService.findAllPaging(no, limit));
	}

	// Get student by Id
	@GetMapping("/{id}")
	public ResponseEntity<CandidateDTO> getById(@PathVariable int id) {
		return ResponseEntity.ok(this.studentService.findById(id));
	}

	// GET CANDIDATE BY JOB ID
	@GetMapping("/job/{id}")
	public ResponseEntity<?> findByJobId(@PathVariable("id") int jobId, @RequestParam("no") int no, @RequestParam("limit") int limit) {
		return ResponseEntity.ok(this.studentService.findByJobIdPaging(jobId, no, limit));
	}
	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable int id) {
		studentService.deleteById(id);
		return ResponseEntity.ok("Student deleted");
	}
	//GET BY MAJOR
	@GetMapping("/major/{id}")
	public ResponseEntity<?> findAllByMajor(@PathVariable int id, @RequestParam("no") int no, @RequestParam("limit") int limit) {
		return ResponseEntity.ok(this.studentService.findAllByMajorPaging(id, no, limit));
	}
	//GET BY USERNAME
	@GetMapping("/u/{username}")
	public ResponseEntity<?> getCandidateByUsername(@PathVariable String username) {
		return ResponseEntity.ok(this.studentService.findByUsername(username));
	}
}
