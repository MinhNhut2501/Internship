package com.r2s.findInternship.restcontroller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.UniversityService;
@CrossOrigin(maxAge = 3600,origins = "*")
@RestController
@RequestMapping("api/university")
@PreAuthorize("isAuthenticated()")
public class UniversityGeneralController {
	@Autowired
	private UniversityService universityService;
	@GetMapping("")
	public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.universityService.findAllPaging(no, limit));
	}
	@GetMapping("get-all/{field}")
	public ResponseEntity<?> getAllSortField(@PathVariable String field)
	{
		return ResponseEntity.ok(this.universityService.getAllSort(field));
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.universityService.findById(id));
	}
	// FIND BY NAME
	@GetMapping("/name")
	public ResponseEntity<?> getByName(@RequestParam("q") String name, @RequestParam("no") int no, @RequestParam("limit") int limit) {
		return ResponseEntity.ok(this.universityService.findByNameContainingPaging(name, no, limit));
	}
	// FIND BY SHORT NAME
	@GetMapping("/shortname")
	public ResponseEntity<?> getByShortName(@RequestParam("q") String shortName, @RequestParam("no") int no, @RequestParam("limit") int limit) {
		return ResponseEntity.ok(this.universityService.findByShortNameContainingPaging(shortName, no, limit));
	}
	@GetMapping("/address/{id}/universities")
	public ResponseEntity<?> findAdress(@PathVariable("id") int provinceId, @RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.universityService.findActiveJobByProvincePagination(provinceId, no, limit));
	}

}