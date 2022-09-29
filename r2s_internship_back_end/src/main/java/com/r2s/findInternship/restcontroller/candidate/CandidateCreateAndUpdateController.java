package com.r2s.findInternship.restcontroller.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.CandidateCreateDTO;
import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.service.CandidateService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/candidate")
public class CandidateCreateAndUpdateController {
	@Autowired
	private CandidateService candidateService;
	@PostMapping(value = "",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> createCandidateUpFile(@RequestPart("candidate") String candidateCreateDTO, 
			@RequestPart(name = "fileCV",required = false) MultipartFile fileCV,
			@RequestPart(name = "fileAvatar",required = false) MultipartFile fileAvatar)
	{
		//Transfer from String ( JSON ) to CandidateDTO
		CandidateCreateDTO candidate = candidateService.readJson(candidateCreateDTO, fileCV,fileAvatar);
		return new ResponseEntity<CandidateDTO>(candidateService.save(candidate), HttpStatus.CREATED);
	}
	//Update student
	@PreAuthorize(value = "hasRole('Candidate')")
	@PutMapping(value ="",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CandidateDTO> update(@RequestPart("candidate") String candidateCreateDTO, 
			@RequestPart(name = "fileCV",required = false) MultipartFile fileCV,
			@RequestPart(name = "fileAvatar",required = false) MultipartFile fileAvatar)
	{
		//Tranfer from String ( JSON ) to CandidateDTO
		CandidateCreateDTO candidate = candidateService.readJson(candidateCreateDTO, fileCV,fileAvatar);
		return ResponseEntity.ok(candidateService.update(candidate));
	}


	@GetMapping("/user/{id}")
	public ResponseEntity<?> getByUserId(@PathVariable("id") int id) {
		return ResponseEntity.ok(candidateService.findByUserId(id));
	}
	
}
