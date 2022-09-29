package com.r2s.findInternship.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.CountriesService;
import com.r2s.findInternship.dto.CountriesDTO;
import com.r2s.findInternship.entity.Countries;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/country")
public class CountriesController {
	@Autowired 
	private CountriesService countriesService;
	
	@GetMapping("")
	public ResponseEntity<List<CountriesDTO>> getAll()
	{
		return ResponseEntity.ok(this.countriesService.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Countries> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.countriesService.findById(id));
	}
	
	@PostMapping("")
	public ResponseEntity<CountriesDTO> create(ModelMap modelMap, @RequestBody CountriesDTO dto)
	{	
		modelMap.addAttribute("message","Country is added !");
		return new ResponseEntity<CountriesDTO>(this.countriesService.save(dto), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CountriesDTO> update(ModelMap modelMap,@PathVariable int id, @RequestBody CountriesDTO dto)
	{
		
		modelMap.addAttribute("message","Country with id = "+id+"is updated !");
		return ResponseEntity.ok(this.countriesService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(ModelMap modelMap, @PathVariable int id)
	{
		this.countriesService.deleteById(id);
		modelMap.addAttribute("message","HR with id = "+id+"is deleted !");
		return ResponseEntity.ok("DELETED");
	}
}
