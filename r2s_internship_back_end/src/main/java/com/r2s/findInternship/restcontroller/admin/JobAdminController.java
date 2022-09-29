package com.r2s.findInternship.restcontroller.admin;

import java.time.LocalDate;
import java.util.Map;

import org.aspectj.weaver.bcel.UnwovenClassFileWithThirdPartyManagedBytecode;
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

import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.LocationDTO;
import com.r2s.findInternship.service.JobService;
import com.r2s.findInternship.service.LocationService;

@RestController
@RequestMapping("api/r2s/admin/job")
@PreAuthorize("hasAuthority('Role_Admin')")
public class JobAdminController {
    @Autowired
    private JobService jobService;
    @Autowired
    private LocationService locationService;

    //GET ALL
    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(jobService.findAllJobPaging(no, limit));
    }

    //GET BY HR USER ID
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByHRId(@PathVariable long id, @RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.jobService.findAllActiveJobByUserIdPaging(id, no, limit));
    }

    // GET BY HR ID
    @GetMapping("/hr/{id}")
    public ResponseEntity<?> findByHRId(@PathVariable("id") int hrId, @RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.jobService.findAllActiveJobByHRIdPaging(hrId, no, limit));
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.jobService.findById(id));
    }
    
    //get by Username
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getByUserName(@PathVariable String username, @RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.jobService.findAllActiveJobByUsernamePaging(username, no, limit));
    }

    //CREATE
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody JobDTO dto) {
        LocationDTO locationDTO = dto.getLocationjob();
        locationDTO = locationService.save(locationDTO);
        dto.setLocationjob(locationDTO);
        return new ResponseEntity<JobDTO>(this.jobService.save(dto), HttpStatus.CREATED);
    }

    //UPDATE BY ID
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody JobDTO dto, @PathVariable int id) {
        LocationDTO locationDTO = dto.getLocationjob();
        locationDTO = locationService.update(locationDTO.getId(), locationDTO);
        dto.setLocationjob(locationDTO);
        return ResponseEntity.ok(this.jobService.update(id, dto));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> changeStatus(@RequestBody JobDTO dto, @PathVariable int id) {
        return ResponseEntity.ok(this.jobService.changeStatus(id, dto));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        this.jobService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }

    //Statistics
    @GetMapping("statistics/newJob")
    public ResponseEntity<?> statisticsByNewUniversity() {
        return ResponseEntity.ok(this.jobService.statisticsNewJob());
    }

    @GetMapping("statistics/countAll")
    public ResponseEntity<?> getCountAll() {
        return ResponseEntity.ok(this.jobService.count());
    }

    @GetMapping("/statistics/countByDate")
    public ResponseEntity<?> statisticsCountByDate(@RequestParam(required = false) Map<String, String> params) {    //INPUT  for example :from=2022-06-01 & 2022-06-30
        String from = params.getOrDefault("from", "");
        String to = params.getOrDefault("to", LocalDate.now().toString());// Neu khong co to thi mac dinh la hien tai
        return ResponseEntity.ok(this.jobService.getCount(from, to));
    }

    @GetMapping("statistics/status")
    public ResponseEntity<?> statisticsStatus() {
        return ResponseEntity.ok(this.jobService.statisticsByStatus());
    }

    // GET jobs by company
    @GetMapping("company/{id}")
    public ResponseEntity<?> getByCompanyId(@PathVariable int id, @RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.jobService.findAllActiveJobByCompanyIdPaging(id, no, limit));
    }


}
