package com.r2s.findInternship.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.findInternship.common.JwtUtils;
import com.r2s.findInternship.dto.JwtRespone;
import com.r2s.findInternship.dto.LoginRequest;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.service.UserService;
import com.r2s.findInternship.service.impl.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    public final Logger logger = LoggerFactory.getLogger("info");

    @PostMapping("api/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        User userDto = userService.findByUsername(user.getUsername());
        long id = userDto.getId();
        List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        logger.info("%s has successfully logged in.", user.getUsername());
        return new ResponseEntity<JwtRespone>(new JwtRespone(jwt, user.getUsername(), user.getEmail(), roles.get(0), userDto.getAvatar(), id), HttpStatus.CREATED);
    }

}
