package com.r2s.findInternship.restcontroller.candidate;

import com.r2s.findInternship.dto.CareListDTO;
import com.r2s.findInternship.service.CareListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/r2s/carelist")
@PreAuthorize("hasAuthorize('Role_Candidate')")
public class CareListCandidateController {
    @Autowired
    private CareListService careListService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.careListService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.careListService.findById(id));
    }

    @GetMapping("/user/{username}/job/{id}")
    public ResponseEntity<?> findByUsernameAndJobId(@PathVariable("username") String username, @PathVariable("id") int jobId) {
        return ResponseEntity.ok(this.careListService.findByUsernameAndJobId(username, jobId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable("username") String username, @RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.careListService.findAllByUsernamePaging(username, pageNo, pageLimit));
    }

    @GetMapping("/candidate/{id}")
    public ResponseEntity<?> findByCandidateId(@PathVariable("id") int candidateId, @RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.careListService.findAllByCandidateIdPaging(candidateId, pageNo, pageLimit));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody CareListDTO careListDTO) {
        return new ResponseEntity<CareListDTO>(this.careListService.save(careListDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody CareListDTO careListDTO) {
        return ResponseEntity.ok(this.careListService.update(id, careListDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.careListService.deleteById(id));
    }
}
