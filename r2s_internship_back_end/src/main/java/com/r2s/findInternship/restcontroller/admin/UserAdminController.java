package com.r2s.findInternship.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.mapstructmapper.MapperUser;
import com.r2s.findInternship.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/r2s/admin/user")
@PreAuthorize("hasAuthority('Role_Admin')")
public class UserAdminController {
	@Autowired
	private MapperUser mapperUser;
	@Autowired
	private UserService userService;
	@GetMapping("")
	public ResponseEntity<?> getAll(@RequestParam("no") int pageNo, @RequestParam("limit") int pageLimit)
	{
		return ResponseEntity.ok(this.userService.findAllPaging(pageNo, pageLimit));
	}
	@GetMapping("/get-username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username)
	{
		return ResponseEntity.ok(this.mapperUser.mapToUserDetails(userService.findByUsername(username)));
	}
	@GetMapping("/get-id/{id}")
	public ResponseEntity<?> getUserByIdUser(@PathVariable long id)
	{
		return ResponseEntity.ok(userService.findById(id));
	}
	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteByUsername(@PathVariable String username)
	{
		return ResponseEntity.ok(userService.deleteById(username));
	}
	@GetMapping("/re/{username}")
	public ResponseEntity<?> recoverByUsername(@PathVariable String username)
	{
		this.userService.recover(username);
		return ResponseEntity.ok("User recovered!");
	}
}
