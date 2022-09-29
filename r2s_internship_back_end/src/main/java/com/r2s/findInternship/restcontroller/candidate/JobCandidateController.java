package com.r2s.findInternship.restcontroller.candidate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/r2s/job")
public class JobCandidateController {
    @Autowired
    private JobService jobService;
    @Autowired
    private LocationService locationService;

    //GET ALL
    @GetMapping("")
    public ResponseEntity<?> findAllPagination(@RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(jobService.findAllActiveJobPaging(pageNo, pageLimit));
    }

    //GET ALL JOB ACTIVE BY USER ID
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findActiveByUserId(@PathVariable long id, @RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.jobService.findAllActiveJobByUserIdPaging(id, pageNo, pageLimit));
    }
    
    //GET ALL JOB DISABLE BY USER ID
    @GetMapping("/user/disable/{id}")
    public ResponseEntity<?> findDisableByUserId(@PathVariable long id, @RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.jobService.findDisableJobByUserIdPaging(id, pageNo, pageLimit));
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(this.jobService.findById(id));
    }
    //get by Username

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUserName(@PathVariable String username, @RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.jobService.findAllActiveJobByUsernamePaging(username, pageNo, pageLimit));
    }

    @GetMapping("/search-location")
    public ResponseEntity<?> findByDistrictAndProvince(@RequestParam("district") String district,
                                                       @RequestParam("province") String province,
                                                       @RequestParam("no") int no,
                                                       @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.jobService.findActiveJobByDistrictAndProvincePaging(district, province, no, limit));
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
        return ResponseEntity.ok(this.jobService.update(id, dto));
    }

    @GetMapping("/search/candidate-care/{id}")
    public ResponseEntity<?> searchByNameAndCandidateCare(@RequestParam("name") String name,
                                                          @PathVariable("id") int candidateId,
                                                          @RequestParam("no") int pageNumber,
                                                          @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.jobService.searchActiveJobByNameAndCandidateCarePaging(name, candidateId, pageNumber, pageLimit));
    }

    @GetMapping("/search/candidate-apply/{id}")
    public ResponseEntity<?> searchByNameAndCandidateApply(@RequestParam("name") String name,
                                                           @PathVariable("id") int candidateId,
                                                           @RequestParam("no") int pageNumber,
                                                           @RequestParam("limit") int pageLimit) {
        return ResponseEntity.ok(this.jobService.searchActiveJobByNameAndCandidateApplyPaging(name, candidateId, pageNumber, pageLimit));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        this.jobService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }

    //search with name or province
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam Map<String, String> params) {
        String name = params.getOrDefault("name", "");
        String province = params.getOrDefault("province", "");
        int pageNo = Integer.parseInt(params.getOrDefault("no", "0"));
        int pageLimit = Integer.parseInt(params.getOrDefault("limit", "10"));
        return ResponseEntity.ok(this.jobService.searchActiveJobByNameAndProvincePaging(name, province, pageNo, pageLimit));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam int no,
                                    @RequestParam int limit,
                                    @RequestParam(required = false) List<String> type,
                                    @RequestParam(required = false) List<String> position,
                                    @RequestParam(required = false) List<String> major,
                                    @RequestParam(required = false, defaultValue = "latest") String order,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String province) {
        return ResponseEntity.ok(jobService.filterPaging(name, province, type, position, major, order, no, limit));
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
    public ResponseEntity<List<JobDTO>> getByJobId(@PathVariable int id) {
        return ResponseEntity.ok(this.jobService.getJobByCompanyId(id));
    }
}
