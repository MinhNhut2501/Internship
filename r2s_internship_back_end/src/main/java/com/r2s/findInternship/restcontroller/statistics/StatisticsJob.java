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

import com.r2s.findInternship.service.JobService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/statistics/job")
@PreAuthorize(value = "hasRole('Admin')")
public class StatisticsJob {
	@Autowired
	private JobService jobService;

	@GetMapping("newJob")
	public ResponseEntity<?> statisticsByNewUniversity()
	{
		return ResponseEntity.ok(this.jobService.statisticsNewJob());
	}
	@GetMapping("All")
	public ResponseEntity<?> getCountAll()
	{
		return ResponseEntity.ok(this.jobService.count());
	}
	@GetMapping("countByDate")
	public ResponseEntity<?> statisticsCountByDate(@RequestParam(required = false) Map<String, String> params)
	{	//INPUT  for example :from=2022-06-01 & 2022-06-30
		String from = params.getOrDefault("from", "");
		String to = params.getOrDefault("to", LocalDate.now().toString());// Neu khong co to thi mac dinh la hien tai
		return ResponseEntity.ok(this.jobService.getCount(from,to));
	}
	@GetMapping("status")
	public ResponseEntity<?> statisticsStatus()
	{
		return ResponseEntity.ok(this.jobService.statisticsByStatus());
	}
	@GetMapping("major")
	public ResponseEntity<?> statisticsMajor()
	{
		return ResponseEntity.ok(this.jobService.statisticsByMajor());
	}
	@GetMapping("JobPosition")
	public ResponseEntity<?> statistics()
	{
		return ResponseEntity.ok(this.jobService.statisticsByJobPosition());
	}
	
}
