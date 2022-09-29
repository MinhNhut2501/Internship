package com.r2s.findInternship.restcontroller.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.dto.RateDTO;
import com.r2s.findInternship.service.RateService;


@RestController
@RequestMapping("api/r2s/rate")
@PreAuthorize("isAuthenticated()")
public class RateCandidateController {
    @Autowired
    private RateService rateService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.rateService.findById(id));
    }

    @GetMapping("/all/company/{id}/user/{username}")
    public ResponseEntity<?> findByCompanyIdAndUsername(@PathVariable("id") int companyId, @PathVariable("username") String username) {
        return ResponseEntity.ok(this.rateService.findByCompanyIdAndUsername(companyId, username));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.rateService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<?> findAllActive() {
        return ResponseEntity.ok(this.rateService.findAllActive());
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> findByCompanyIdToShowClientPage(@PathVariable("id") int companyId, @RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.rateService.findAllByCompanyIdPaging(companyId, pageNo, pageLimit));
    }

    @GetMapping("/company/{id}/user/{username}")
    public ResponseEntity<?> findByCompanyIdAndUsernameActive(@PathVariable("id") int companyId, @PathVariable("username") String username) {
        return ResponseEntity.ok(this.rateService.findByCompanyIdAndUsernameToShowClient(companyId, username));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody RateDTO rateDTO) {
        return new ResponseEntity<RateDTO>(this.rateService.save(rateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody RateDTO rateDTO) {
        return ResponseEntity.ok(this.rateService.update(id, rateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.rateService.deleteById(id));
    }
    
    //Get by companyId
    @GetMapping("/paging/company/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id") int id, @RequestParam("no") int no,@RequestParam("limit") int limit){
    	return ResponseEntity.ok(rateService.findAllByCompanyIdPaging(id,no,limit));
    }

 
}
