package com.r2s.findInternship.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.DistrictService;
import com.r2s.findInternship.dto.DistrictDTO;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/district")
public class DistrictController {
	
	@Autowired
	private DistrictService  DistrictService ;
	
	
	@GetMapping("")
	public ResponseEntity<List<DistrictDTO>> getAll()
	{
		return ResponseEntity.ok(this.DistrictService.findAll());
	}
	
	@PostMapping("")
	public ResponseEntity<DistrictDTO> create( @RequestBody DistrictDTO dto)
	{	
		return new ResponseEntity<DistrictDTO>(this.DistrictService.save(dto), HttpStatus.CREATED);
	}
  
	
	@GetMapping("/{id}")
	public ResponseEntity<DistrictDTO> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.DistrictService.findById(id));
	}
	
	@GetMapping("/province/{id}")
	public ResponseEntity<List<DistrictDTO>> getDistrictByProvinceId(@PathVariable int id)
	{
		return ResponseEntity.ok(this.DistrictService.getDistrictByProvinceId(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DistrictDTO> update(@PathVariable int id, @RequestBody DistrictDTO dto)
	{
		
		return ResponseEntity.ok(this.DistrictService.update(id, dto));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete( @PathVariable int id)
	{
	
		this.DistrictService.deleteById(id);
		return ResponseEntity.ok("DELETED");
	}

}
