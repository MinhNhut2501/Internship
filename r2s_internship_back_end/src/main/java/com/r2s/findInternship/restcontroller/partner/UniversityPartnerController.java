package com.r2s.findInternship.restcontroller.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.PartnerCreateDTO;
import com.r2s.findInternship.dto.PartnerDTO;
import com.r2s.findInternship.dto.UniversityCreateDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.entity.Partner;
import com.r2s.findInternship.mapstructmapper.MapperPartner;
import com.r2s.findInternship.service.PartnerService;
import com.r2s.findInternship.service.UniversityService;
@CrossOrigin(maxAge = 3600,origins = "*")
@RestController
@RequestMapping("api/r2s/partner")

public class UniversityPartnerController {
	@Autowired
	private UniversityService universityService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private MapperPartner mapperPartner;
	//ADD WITH USER
	@PostMapping(value="/university/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> addWithFile(@RequestPart("university") String university,
			@RequestPart("partner") String partner,
			@RequestPart(name = "avatar", required = false) MultipartFile avatarUser,
			@RequestPart(name = "logo",required = false) MultipartFile avatarUniversity)
	{
		// Read String from json
		UniversityCreateDTO universityDTO = this.universityService.readJson(university, partner, avatarUser, avatarUniversity);
		return new ResponseEntity<UniversityDTO>(this.universityService.saveFirst(universityDTO), HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id)
	{
		PartnerDTO entity = this.partnerService.findById(id);
		return ResponseEntity.ok(entity);
	}
	@GetMapping("user/{id}")
	public ResponseEntity<?> getByIdUser(@PathVariable int id)
	{
		Partner entity = this.partnerService.findByUserId(id);
		PartnerDTO dto = mapperPartner.map(entity);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping(value = "",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<PartnerDTO> update(@RequestPart("partner") String partnerCreateDTO){
		//Tranfer from String ( JSON ) to PartnerDTO
		PartnerCreateDTO partner = partnerService.readJson(partnerCreateDTO);
		return ResponseEntity.ok(partnerService.updateUser(partner));
	}
	
}
	

