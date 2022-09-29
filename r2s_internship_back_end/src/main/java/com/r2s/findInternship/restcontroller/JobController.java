package com.r2s.findInternship.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.service.JobService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/job")
public class JobController {
    @Autowired
    private JobService jobService;

    //GET ALL
    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(jobService.findAllActiveJobPaging(no, limit));
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.jobService.findById(id));
    }

    //GET BY ID USER
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByHRId(@PathVariable int id) {
        return ResponseEntity.ok(this.jobService.getJobByUserId(id));
    }

    //CREATE
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody JobDTO dto) {
        return new ResponseEntity<JobDTO>(this.jobService.save(dto), HttpStatus.CREATED);
    }

    //UPDATE BY ID
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody JobDTO dto, @PathVariable int id) {
        return ResponseEntity.ok(this.jobService.update(id, dto));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        this.jobService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }

    // GET jobs by company
    @GetMapping("company/{id}")
    public ResponseEntity<List<?>> getByJobId(@PathVariable int id) {
        return ResponseEntity.ok(this.jobService.getJobByCompanyId(id));
    }
}