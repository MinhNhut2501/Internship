package com.r2s.findInternship.dto;

import org.springframework.web.multipart.MultipartFile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsDTO extends UserDTO {
	private String firstName;
	private String lastName;
	private String avatar;
	private MultipartFile fileAvatar;
}
