package com.r2s.findInternship.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.r2s.findInternship.dto.LocationDTO;
import com.r2s.findInternship.service.LocationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    // GET all location
    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam("no") int no, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.locationService.findAll());
    }

    // POST location
    @PostMapping("")
    public ResponseEntity<LocationDTO> create(@RequestBody LocationDTO dto) {
        return new ResponseEntity<LocationDTO>(this.locationService.save(dto), HttpStatus.CREATED);
    }

    // GET location by id
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.locationService.findById(id));
    }

    // GET Location by District Id
    @GetMapping("district/{id}")
    public ResponseEntity<List<LocationDTO>> getByDistrictId(@PathVariable int id) {
        return ResponseEntity.ok(this.locationService.getLocationByDistrictId(id));
    }

    // GET location of company
    @GetMapping("company/{id}")
    public ResponseEntity<List<LocationDTO>> getByCompanyId(@PathVariable int id) {
        return ResponseEntity.ok(this.locationService.getLocationByCompanyId(id));
    }

    // Update location
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> update(@PathVariable int id, @RequestBody LocationDTO dto) {
        return ResponseEntity.ok(this.locationService.update(id, dto));
    }

    // Delete location
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {

        this.locationService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }
}
