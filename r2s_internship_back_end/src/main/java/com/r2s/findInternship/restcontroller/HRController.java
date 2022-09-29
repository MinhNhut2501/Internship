package com.r2s.findInternship.restcontroller;

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

import com.r2s.findInternship.dto.HRDTO;
import com.r2s.findInternship.dto.show.HRDTOShow;
import com.r2s.findInternship.mapstructmapper.MapperHR;
import com.r2s.findInternship.service.HRService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/hr")
public class HRController {
	
	@Autowired
	private HRService  hrService ;
	@Autowired
	private MapperHR   mapperHR ;
	

	@PreAuthorize("hasAuthority('Role_HR')")
	@GetMapping("")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.ok(this.hrService.findAll());
	}
	
	@PostMapping(value = "",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> create(@RequestPart("hr") String hrStr, 
			@RequestPart(name = "fileAvatar",required = false) MultipartFile fileAvatar)
	{	
		HRDTO hrDTO = hrService.readJson(hrStr,fileAvatar);		
		return new ResponseEntity<HRDTOShow>(mapperHR.mapHRShow(mapperHR.map(hrService.save(hrDTO))), HttpStatus.CREATED);

	}
	
	@PreAuthorize("hasAuthority('Role_HR')")
	@PutMapping(value = "/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> update(@PathVariable int id,@RequestPart("hr") String hrStr, 
			@RequestPart(name = "fileAvatar",required = false) MultipartFile fileAvatar)
	{
		HRDTO hrDTO = hrService.readJson(hrStr,fileAvatar);		
		return ResponseEntity.ok(this.hrService.update(id, hrDTO));
	}


	@PreAuthorize("hasAuthority('Role_HR')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.hrService.findByIdToShow(id));
	}
	
	//GET BY ID USER

	@PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByHRId(@PathVariable long id)
	{
		return ResponseEntity.ok(this.hrService.findByUserId(id));
	}
	
	@PreAuthorize("hasAuthority('Role_HR')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete( @PathVariable int id)
	{
		
		this.hrService.deleteById(id);
		return ResponseEntity.ok("DELETED");
	}
}
