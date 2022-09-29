package com.r2s.findInternship.restcontroller.general;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.common.MailResponse;
import com.r2s.findInternship.common.TypeMail;
import com.r2s.findInternship.dto.ApplyListDTO;
import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.service.ApplyListService;
import com.r2s.findInternship.service.CandidateService;
import com.r2s.findInternship.service.JobService;
import com.r2s.findInternship.service.MailService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/applylist")
public class ApplyListGeneralController {
	@Autowired
	private ApplyListService  applyListService ;
	@Autowired
	private MailService mailService;
	@Autowired
	private JobService jobService;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private MessageSource messageSource;
	
	//Get  all 
	@GetMapping("")
	public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.applyListService.findAllPaging(no,limit));
	}
	
	// Apply  
	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@RequestPart("apply") String apply,
			@RequestPart(name = "fileCV", required = false) MultipartFile fileCV)
	{			

		ApplyListDTO applyListDTO = applyListService.readJson(apply, fileCV);
		//check job apply
		JobDTO job = jobService.findById(applyListDTO.getJobApp().getId());
		applyListDTO.setJobApp(job);
		//check candidate apply
		CandidateDTO candidateDTO = candidateService.findById(applyListDTO.getCandidate().getId()); 
		applyListDTO.setCandidate(candidateDTO);
		//check apply
		if(applyListService.checkApply(applyListDTO.getJobApp().getId(), applyListDTO.getCandidate().getId())==false) {
		    return ResponseEntity.ok(new ResponeMessage(500, String.format(this.messageSource.getMessage("error.ApplyAlready", null, null), applyListDTO.getId())));
		}
		//create Date
		applyListDTO.setCreateDate(LocalDate.now());
		MailResponse mailResponse = new MailResponse();
		
		mailResponse.setTo(job.getHr().getUser().getEmail());
		mailResponse.setTypeMail(TypeMail.ApplyJob);	
		mailResponse.setCv(applyListDTO.getCV());
		mailResponse.setApply(applyListDTO);
		mailService.send(mailResponse);
		return new ResponseEntity<ApplyListDTO>(this.applyListService.save(applyListDTO), HttpStatus.CREATED);

	}
  
	//Check apply
	@GetMapping("/check")
	public ResponseEntity<?> checkApplyById(@RequestParam Map<String, String> params)
	{
		int jobId = Integer.parseInt(params.getOrDefault("jobid", ""));
		int candidateId = Integer.parseInt(params.getOrDefault("candidateid", ""));
		if(applyListService.checkApply(jobId, candidateId)==true)
			return ResponseEntity.ok( new ResponeMessage(200, String.format(this.messageSource.getMessage("info.checkApply", null, null), jobId)));
	    return ResponseEntity.ok(new ResponeMessage(500, String.format(this.messageSource.getMessage("error.ApplyAlready", null, null), jobId)));
	
	}
	// Find by id
	@GetMapping("/{id}")
	public ResponseEntity<ApplyListDTO> getById(@PathVariable int id)
	{
		return ResponseEntity.ok(this.applyListService.findById(id));
	}
	
	
	// Get by job id
	@GetMapping("/job/{id}")
	public ResponseEntity<?> getApplyListByJobId(@PathVariable int id,@RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.applyListService.findByJobIdPaging(id,no,limit));
	}
	
	
	// Get by Candidate id
	@GetMapping("/candidate/{id}")
	public ResponseEntity<?> getApplyListByCandidateId(@PathVariable int id,@RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.applyListService.findByCandidateIdPaging(id,no,limit));
	}

	@GetMapping("/candidate/user/{username}")
	public ResponseEntity<?> getApplyListByUsername(@PathVariable("username") String username, @RequestParam("no") int no, @RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.applyListService.findByUsernamePaging(username, no, limit));
	}
	
	
	//Update by id
	@PutMapping("/{id}")
	public ResponseEntity<ApplyListDTO> update(@PathVariable int id, @RequestBody ApplyListDTO dto)
	{
		
		return ResponseEntity.ok(this.applyListService.update(id, dto));
	}
	
	// Delete by id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete( @PathVariable int id)
	{
	
		this.applyListService.deleteById(id);
		return ResponseEntity.ok("DELETED");
	}
}
