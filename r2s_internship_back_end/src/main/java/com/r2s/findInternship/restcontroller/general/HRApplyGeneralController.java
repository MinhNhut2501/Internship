package com.r2s.findInternship.restcontroller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.dto.HRApplyListDTO;
import com.r2s.findInternship.service.HRApplyListService;

@CrossOrigin(maxAge = 3600,origins = "*")
@RestController
@RequestMapping("api/r2s/HRApply")
@PreAuthorize("hasAuthoriity('Role_HR')")
public class HRApplyGeneralController {
	@Autowired
	private HRApplyListService hrApplyListService;
	@PostMapping("")
	public ResponseEntity<?> save(@RequestBody HRApplyListDTO hrApplyListDTO)
	{
		return new ResponseEntity<HRApplyListDTO>(hrApplyListService.save(hrApplyListDTO), HttpStatus.CREATED);
	}
}
