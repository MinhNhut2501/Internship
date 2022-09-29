package com.r2s.findInternship.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.r2s.findInternship.dto.ChangePassDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.UserCreationDTO;
import com.r2s.findInternship.dto.UserDTO;
import com.r2s.findInternship.dto.UserDetailsDTO;
import com.r2s.findInternship.dto.UserRegisterDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.User;

public interface UserService extends UserDetailsService {

    boolean existsById(long id);

    List<UserDTO> findAll();

    User findByUsername(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDTO save(UserCreationDTO user);

    User save(User user);

    UserCreationDTO handlerValid(UserCreationDTO entity);

    List<User> findAll(Sort sort);

    PaginationDTO findAllPaging(int no, int limit);

    //	UserDTO save(UserCreationDTO dto);
    void changePassword(ChangePassDTO dto);

    UserDTO update(UserDetailsDTO user);

    ResponeMessage deleteById(String id);

    ResponeMessage recover(String username);

    ResponeMessage sendMailForgotPassword(String email);

    UserDetailsDTO findById(long username);

    boolean checkIfValidOldPassword(String oldPass, String newPass);

    List<Object[]> statisticsBySex();

    Long getCount(String from, String to);

    List<Object[]> statisticsByRole();

    List<Object[]> statisticsByStatus();

    Long statisticsNewUser();

    boolean existsByEmail(String email);

    String resetPassword(String email);

    boolean existsByUsername(String username);

    String encodePass(String pass);

    User findByEmail(String email);

	User saveAdmin(User user);
	
	PaginationDTO getUserByUsernameAndPaging(String username,int no,int limit);
	
	


}
