package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.UserCreationDTO;
import com.r2s.findInternship.dto.UserDTO;
import com.r2s.findInternship.dto.UserDetailsDTO;
import com.r2s.findInternship.entity.User;

@Mapper(componentModel = "spring")
public interface MapperUser {
	User map(UserDTO dto);
	UserDTO map(User entity);
	User map(UserCreationDTO dto);
	User map(UserDetailsDTO dto);
	UserDetailsDTO mapToUserDetails(User user);
	UserCreationDTO mapToCreate(User user);
}
