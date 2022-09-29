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

import com.r2s.findInternship.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/statistics/user")
@PreAuthorize(value = "hasRole('Admin')")
public class StatisticsUser {
	@Autowired
	private UserService userService;
	@GetMapping("/statistics/sex")
	public ResponseEntity<?> statisticsBySex()
	{
		return ResponseEntity.ok(this.userService.statisticsBySex());
	}
	@GetMapping("/statistics/countByDate")
	public ResponseEntity<?> statisticsCountByDate(@RequestParam(required = false) Map<String, String> params)
	{	
		String from = params.getOrDefault("from", "");
		String to = params.getOrDefault("to", LocalDate.now().toString());
		return ResponseEntity.ok(this.userService.getCount(from,to));
	}
	@GetMapping("/statistics/role")
	public ResponseEntity<?> statisticsRole()
	{	
		return ResponseEntity.ok(this.userService.statisticsByRole());
	}
	@GetMapping("/statistics/status")
	public ResponseEntity<?> statisticsStatus()
	{	
		return ResponseEntity.ok(this.userService.statisticsByStatus());
	}
	@GetMapping("/statistics/new")
	public ResponseEntity<?> statisticsNew()
	{	
		return ResponseEntity.ok(this.userService.statisticsNewUser());
	}

}
