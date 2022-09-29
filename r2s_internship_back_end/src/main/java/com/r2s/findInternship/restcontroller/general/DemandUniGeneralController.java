package com.r2s.findInternship.restcontroller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.DemandUniService;

@RestController
@RequestMapping("api/demand")
public class DemandUniGeneralController {
    @Autowired
    private DemandUniService demandUniService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.demandUniService.findAllPagingLatest(no, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.demandUniService.findById(id));
    }

    @GetMapping("filter-university/{id}")
    public ResponseEntity<?> filterUniversity(@PathVariable int id, @RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.demandUniService.findByUniversityIdPaging(id, no, limit));
    }

}
