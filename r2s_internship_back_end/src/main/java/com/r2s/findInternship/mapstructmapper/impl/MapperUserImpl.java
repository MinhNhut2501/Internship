package com.r2s.findInternship.mapstructmapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.dto.UserCreationDTO;
import com.r2s.findInternship.dto.UserDTO;
import com.r2s.findInternship.dto.UserDetailsDTO;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.mapstructmapper.MapperStatus;
import com.r2s.findInternship.mapstructmapper.MapperUser;

@Component
public class MapperUserImpl implements MapperUser {
    @Autowired
    MapperStatus mapperStatus;

    @Override
    public User map(UserDTO dto) {
        if (dto == null) return null;
        User u = new User();
        u.setId(dto.getId());
        u.setEmail(dto.getEmail());
        u.setGender(dto.getGender());
        u.setRole(dto.getRole());
        u.setUsername(dto.getUsername());
        u.setStatus(dto.getStatus());
        u.setPhone(dto.getPhone());
        return u;
    }

    @Override
    public UserDTO map(User dto) {
        if (dto == null) return null;
        UserDTO u = new UserDTO();
        u.setId(dto.getId());
        u.setEmail(dto.getEmail());
        u.setGender(dto.getGender());
        u.setRole(dto.getRole());
        u.setUsername(dto.getUsername());
        u.setStatus(dto.getStatus());
        u.setPhone(dto.getPhone());
        return u;
    }

    @Override
    public User map(UserCreationDTO dto) {
        if (dto == null)
            return null;
        User user = new User();
        if (!dto.getUsername().isEmpty())
            user.setUsername(dto.getUsername());
        if (!dto.getPassword().isEmpty())
            user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());
        if (dto.getAvatar() != null)
            user.setAvatar(dto.getAvatar());
        else user.setAvatar("");
        if (dto.getRole() != null) user.setRole(dto.getRole());
        if (dto.getStatus() != null) user.setStatus(dto.getStatus());
        return user;
    }

    @Override
    public User map(UserDetailsDTO dto) {
        if (dto == null)
            return null;
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setGender(dto.getGender());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setStatus(dto.getStatus());
        user.setAvatar(dto.getAvatar());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

    @Override
    public UserDetailsDTO mapToUserDetails(User user) {
        if (user == null)
            return null;
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setGender(user.getGender());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setAvatar(user.getAvatar());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

    @Override
    public UserCreationDTO mapToCreate(User user) {
        if (user == null)
            return null;
        UserCreationDTO userDTO = new UserCreationDTO();
        userDTO.setUsername(user.getUsername());
        if (!user.getPassword().isEmpty()) {
            userDTO.setPassword(user.getPassword());
            userDTO.setConfirmPassword(user.getPassword());
        }
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setGender(user.getGender());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());
        userDTO.setAvatar(user.getAvatar());
        return userDTO;
    }
}
