package com.r2s.findInternship.restcontroller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.common.MailResponse;
import com.r2s.findInternship.common.TypeMail;
import com.r2s.findInternship.dto.ChangePassDTO;
import com.r2s.findInternship.dto.UserCreationDTO;
import com.r2s.findInternship.dto.UserDTO;
import com.r2s.findInternship.dto.UserRegisterDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.service.MailService;
import com.r2s.findInternship.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("")
    public UserDTO checkUser(@Valid @RequestBody UserCreationDTO dto) {
        return this.userService.handlerValid(dto);
    }

    @PutMapping("")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserCreationDTO dto) {
        return ResponseEntity.ok(this.userService.update(dto));
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@Valid @RequestBody UserCreationDTO dto) {
        return new ResponseEntity<UserDTO>(this.userService.save(dto), HttpStatus.CREATED);
    }

    @PostMapping(value = "uploadFile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//TEST
    public ResponseEntity<?> updateFile(@RequestPart("user") String user, @RequestPart(value = "file", required = false) MultipartFile file) {
        UserCreationDTO userdto = new UserCreationDTO();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userdto = objectMapper.readValue(user, UserCreationDTO.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        userdto.setFileAvatar(file);

        return new ResponseEntity<UserDTO>(this.userService.save(userdto), HttpStatus.CREATED);
    }


    @GetMapping("/forgotPassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email) {
        return ResponseEntity.ok(userService.sendMailForgotPassword(email));
    }

    @PutMapping("/changePassword")
    public ResponeMessage changePassword(@Valid @RequestBody ChangePassDTO pass) {
        this.userService.changePassword(pass);
    	return new ResponeMessage(200, messageSource.getMessage("error.userChangePasswordSuccessfull", null, null));
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
    	return ResponseEntity.ok(userService.saveAdmin(user));
    }
    
    @GetMapping("/search/{username}")
    public ResponseEntity<?> getUserByUsernameAndPaging(@PathVariable("username") String username,@RequestParam("no") int no,
    		                                            @RequestParam("limit") int limit){
    	return ResponseEntity.ok(userService.getUserByUsernameAndPaging(username, no, limit));
    }
}

