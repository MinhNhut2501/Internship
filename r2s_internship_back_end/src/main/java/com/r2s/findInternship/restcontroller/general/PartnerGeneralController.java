package com.r2s.findInternship.restcontroller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.PartnerService;

@CrossOrigin(maxAge = 3600,origins = "*")
@RestController
@RequestMapping("api/r2s/partner")
public class PartnerGeneralController {
	@Autowired
	private PartnerService partnerService;
	@GetMapping("/university/{id}")
	public ResponseEntity<?> getPartnerByUniversity(@PathVariable int id)
	{
		return ResponseEntity.ok(this.partnerService.getPartnersByUniversity(id));
	}
	
}
