package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.DemandUniDTO;
import com.r2s.findInternship.dto.DemandUniShowDTO;
import com.r2s.findInternship.exception.ResponeMessage;
import com.r2s.findInternship.service.DemandUniService;

@CrossOrigin(maxAge = 3600,origins = "*")
@RestController
@RequestMapping("api/r2s/partner/demand")
@PreAuthorize("hasAnyAuthority('Role_Partner','Role_Admin')")
public class DemandAdminController {
	@Autowired
	private DemandUniService demandUniService;

	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })//ADMIN
	public ResponseEntity<?> create(@RequestPart("demand") String candidateCreateDTO,
			@RequestPart(name = "fileSV", required = false) MultipartFile file) {
		DemandUniDTO dto = this.demandUniService.readJson(candidateCreateDTO, file);//Tranfer String to Object
		return new ResponseEntity<DemandUniShowDTO>(this.demandUniService.save(dto), HttpStatus.CREATED);
	}
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> update(@PathVariable int id, @RequestPart("demand") String candidateCreateDTO,
			@RequestPart(name = "fileSV", required = false) MultipartFile file) {
		DemandUniDTO dto = this.demandUniService.readJson(candidateCreateDTO, file);//Tranfer String to Object
		return ResponseEntity.ok(this.demandUniService.update(dto, id));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id)
	{
		this.demandUniService.delete(id);
		return ResponseEntity.ok(new ResponeMessage(200, "Demand University deleted!"));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchByName(@RequestParam("name") String name, @RequestParam("no") int no, @RequestParam("limit") int limit) {
		return ResponseEntity.ok(this.demandUniService.searchByNamePagingLatest(name, no, limit));
	}

}
