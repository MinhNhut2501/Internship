package com.r2s.findInternship.restcontroller.candidate;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.service.CompanyLocationService;
import com.r2s.findInternship.service.CompanyService;
import com.r2s.findInternship.service.LocationService;
import com.r2s.findInternship.dto.CompanyDTO;
import com.r2s.findInternship.dto.CompanyLocationDTO;
import com.r2s.findInternship.dto.LocationDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/company")
public class CompanyCandidateController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private CompanyLocationService companyLocationService;

    // Get all order by Name
    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.companyService.findAllActivePaging(no, limit));
    }

    // Find by name
    @GetMapping("/search")
    public ResponseEntity<?> findByName(@RequestParam("q") String name, @RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.companyService.findByNameContainingPaging(name, no, limit));
    }


    // Save
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@RequestPart("company") String companyJson, @RequestPart("location") String locationJson,
                                    @RequestPart(name = "fileLogo", required = false) MultipartFile fileLogo) {
        //location
        LocationDTO locationDTO = locationService.readJson(locationJson);
        locationDTO = locationService.save(locationDTO);
        //company
        CompanyDTO companyDTO = this.companyService.readJson(companyJson, fileLogo);
        companyService.checkCompany(companyDTO);    //Kiểm tra trùng
        companyDTO = companyService.save(companyDTO);
        //location of company
        CompanyLocationDTO companyLocationDTO = new CompanyLocationDTO();
        companyLocationDTO.setCompanyDTO(companyDTO);
        companyLocationDTO.setLocationDTO(locationDTO);

        companyLocationService.save(companyLocationDTO);
        return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.CREATED);
    }

    // Update by ID
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@PathVariable int id, @RequestPart("company") String companyJson,
                                    @RequestPart(name = "fileLogo", required = false) MultipartFile fileLogo) {
        CompanyDTO companyDTO = this.companyService.readJson(companyJson, fileLogo);
        companyService.checkCompany(id, companyDTO); // Kiểm tra trùng
        return ResponseEntity.ok(this.companyService.update(id, companyDTO));
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.companyService.findById(id));
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        this.companyService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }

    //Statistics

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/statistics/countByDate")
    public ResponseEntity<?> statisticsCountByDate(@RequestParam(required = false) Map<String, String> params) {
        String from = params.getOrDefault("from", "");
        String to = params.getOrDefault("to", LocalDate.now().toString());
        return ResponseEntity.ok(this.companyService.getCountByDate(from, to));
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/statistics/new")
    public ResponseEntity<?> statisticsNew() {
        return ResponseEntity.ok(this.companyService.statisticsNewCompany());
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/statistics/status")
    public ResponseEntity<?> statisticsStatus() {
        return ResponseEntity.ok(this.companyService.statisticsByStatus());
    }


}
