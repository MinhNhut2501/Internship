package com.r2s.findInternship.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.r2s.findInternship.dto.HRApplyListDTO;
import com.r2s.findInternship.service.HRApplyListService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/hrapplylist")
@PreAuthorize("hasAuthority('Role_HR')")
public class HRApplyListController {
    @Autowired
    private HRApplyListService hrApplyListService;

    // Get all order by Name
    @GetMapping("")
    public ResponseEntity<?> findAllLatest(@RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.hrApplyListService.findAllPagingLatest(no, limit));
    }

    // Save
    @PostMapping("")
    public ResponseEntity<HRApplyListDTO> create(@RequestBody HRApplyListDTO dto) {
        return new ResponseEntity<HRApplyListDTO>(this.hrApplyListService.save(dto), HttpStatus.CREATED);
    }

    // Update by ID
    @PutMapping("/{id}")
    public ResponseEntity<HRApplyListDTO> update(@PathVariable int id, @RequestBody HRApplyListDTO dto) {

        return ResponseEntity.ok(this.hrApplyListService.update(id, dto));
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<HRApplyListDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.hrApplyListService.findById(id));
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        this.hrApplyListService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }
}
