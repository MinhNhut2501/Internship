package com.r2s.findInternship.restcontroller.admin;

import com.r2s.findInternship.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/r2s/admin/rate")
@PreAuthorize("hasAuthority('Role_Admin')")
public class RateAdminController {
    @Autowired
    private RateService rateService;
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.rateService.deleteById(id));
    }
    @GetMapping("re/{id}")
    public ResponseEntity<?> recoverById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.rateService.recover(id));
    }
    
    //Get by companyId
    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id") int id, @RequestParam("no") int no, @RequestParam("limit") int limit){
    	return ResponseEntity.ok(rateService.findAllByCompanyIdPaging(id,no, limit));
    }
}
