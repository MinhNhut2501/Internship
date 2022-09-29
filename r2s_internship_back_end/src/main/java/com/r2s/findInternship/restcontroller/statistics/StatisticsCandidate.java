package com.r2s.findInternship.restcontroller.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.service.CandidateService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/statistics/candidate")
public class StatisticsCandidate {
	@Autowired
	private CandidateService studentService;
	//Statistics By Major
	@GetMapping("")
	public ResponseEntity<?> statisticsByMajor()
	{
		return ResponseEntity.ok(this.studentService.statisticsByMajor());
	}
}
