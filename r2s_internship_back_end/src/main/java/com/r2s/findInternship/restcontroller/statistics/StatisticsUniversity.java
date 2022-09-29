package com.r2s.findInternship.restcontroller.statistics;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.UniversityService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/statistics/university")
@PreAuthorize(value = "hasRole('Admin')")
public class StatisticsUniversity {
	@Autowired
	private UniversityService universityService;
	//Statistics
	@GetMapping("statistics/newUniversity")
	public ResponseEntity<?> statisticsByNewUniversity()
	{
		return ResponseEntity.ok(this.universityService.statisticsNewUniversity());
	}
	@GetMapping("statistics/countAll")
	public ResponseEntity<?> getCountAll()
	{
		return ResponseEntity.ok(this.universityService.count());
	}
	@GetMapping("/statistics/countByTime")
	public ResponseEntity<?> statisticsCountByDate(@RequestParam(required = false) Map<String, String> params)
	{	
		String from = params.getOrDefault("from", "");
		String to = params.getOrDefault("to", LocalDate.now().toString());
		return ResponseEntity.ok(this.universityService.getCount(from,to));
	}
	@GetMapping("statistics/status")
	public ResponseEntity<?> statisticsStatus()
	{
		return ResponseEntity.ok(this.universityService.statisticsByStatus());
	}
}
