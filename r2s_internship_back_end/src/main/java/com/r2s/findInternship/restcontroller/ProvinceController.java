package com.r2s.findInternship.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.ProvinceService;
import com.r2s.findInternship.dto.ProvinceDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;


    @GetMapping("")
    public ResponseEntity<List<ProvinceDTO>> getAll() {
        return ResponseEntity.ok(this.provinceService.findAll());
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<List<ProvinceDTO>> getByCountryId(@PathVariable int id) {
        return ResponseEntity.ok(this.provinceService.getProvinceByCountryId(id));
    }

    @PostMapping("")
    public ResponseEntity<ProvinceDTO> create(@RequestBody ProvinceDTO dto) {
        return new ResponseEntity<ProvinceDTO>(this.provinceService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinceDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.provinceService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceDTO> update(@PathVariable int id, @RequestBody ProvinceDTO dto) {

        return ResponseEntity.ok(this.provinceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {

        this.provinceService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }

}
