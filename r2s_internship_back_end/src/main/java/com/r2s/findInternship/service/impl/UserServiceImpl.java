package com.r2s.findInternship.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.r2s.findInternship.common.*;
import com.r2s.findInternship.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.dto.ChangePassDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.UserCreationDTO;
import com.r2s.findInternship.dto.UserDTO;
import com.r2s.findInternship.dto.UserDetailsDTO;
import com.r2s.findInternship.dto.UserRegisterDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.InvalidOldPasswordException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperUser;
import com.r2s.findInternship.repository.UserRepository;
import com.r2s.findInternship.service.RoleService;
import com.r2s.findInternship.service.StatusService;
import com.r2s.findInternship.service.UserService;
import com.r2s.findInternship.util.UpdateFile;
import com.r2s.findInternship.util.Validation;

import net.bytebuddy.asm.Advice.Return;
import net.bytebuddy.utility.RandomString;

@Service("userDetailsService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private MapperUser mapperUser;

	@Autowired
	private MailService mailService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private Validation validation;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UpdateFile updateFile;
	@Autowired
	private StatusService statusService;
	public final Logger logger = LoggerFactory.getLogger("info");

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(messageSource.getMessage("error.usernameIncorrect", null, null));
		}
		return UserDetailsImpl.build(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(messageSource.getMessage("error.usernameIncorrect", null, null)));
	}

	@Override
	public UserCreationDTO handlerValid(UserCreationDTO entity) // Check valid
	{
		Map<String, String> errors = new HashMap<String, String>();
		if (!entity.getPassword().equals(entity.getConfirmPassword())) {
			errors.put("Password", messageSource.getMessage("error.passwordIncorrect", null, null));
		}
		if (!this.validation.passwordValid(entity.getPassword())) {
			errors.put("Password", messageSource.getMessage("error.passwordRegex", null, null));
		}

		if (existsByEmail(entity.getEmail())) {
			errors.put("Email", messageSource.getMessage("error.emailExists", null, null));
		}

		if (existsByUsername(entity.getUsername())) {
			errors.put("Username", messageSource.getMessage("error.usernameExists", null, null));
		}
		if (errors.size() > 0)
			throw new InternalServerErrorException(errors);
		return entity;
	}

	@Override
	public List<UserDTO> findAll() {
		return userRepository.findAll().stream().map(item -> this.mapperUser.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public boolean existsById(long id) {
		return userRepository.existsById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public UserDTO save(UserCreationDTO user)// SAVE USERNAME AND PASSWORD
	{
		User entity = this.mapperUser.map(handlerValid(user));
		String pass = entity.getPassword();
		entity.setPassword(this.passwordEncoder.encode(pass));
		entity.setCreateDate(LocalDate.now());
			if(user.getRole() == null)
			{
				entity.setRole(roleService.findByName(ERole.Cadidate));//DEFAULT ROLE CANDIDATE
				entity.setStatus(this.statusService.findByName(Estatus.Active));
			}
		if (user.getFileAvatar() != null)// UPDATE AVATAR
		{
			FileUpload file = new FileUpload(user.getFileAvatar(), "");
			this.updateFile.update(file);
			entity.setAvatar(file.getOutput());
		}
		entity = this.userRepository.save(entity);

		logger.info("User " + user.getUsername() + " register successfull!");
		return this.mapperUser.map(entity);
	}

	@Override
	public PaginationDTO findAllPaging(int no, int limit) {
		Pageable page = PageRequest.of(no, limit);// Page: 0 and Member: 10
		List<Object> listUser = this.userRepository.findAll(page).toList().stream()
				.map(item -> this.mapperUser.map(item)).collect(Collectors.toList());
		Page<User> pageUser = this.userRepository.findAll(page);
		return new PaginationDTO(listUser, pageUser.isFirst(), pageUser.isLast(), pageUser.getTotalPages(),
				pageUser.getTotalElements(), pageUser.getSize(), pageUser.getNumber());
	}
	@Override
	public String encodePass(String pass) {
		return this.passwordEncoder.encode(pass);
	}
	@Override
	public List<User> findAll(Sort sort) {
		return userRepository.findAll(sort);
	}

	@Override
	public UserDTO update(UserDetailsDTO user)// UPDATE INFORMATION AND UPLOAD AVATAR
	{
		User findingUser = this.findByUsername(user.getUsername());
		findingUser.setId(findingUser.getId());
		if (findingUser == null)
			throw new ResourceNotFound("User", "username", user.getUsername());
		if (user.getFileAvatar() != null)// UPDATE AVATAR, Quy Trinh: Co File => Kiem Tra AVATAR ton tai => Update
		{
			FileUpload file = new FileUpload();
			file.setFile(user.getFileAvatar());
			String url =findingUser.getAvatar();
			if(!url.equals(""))
				this.updateFile.deleteFile(url);

			this.updateFile.update(file);
			findingUser.setAvatar(file.getOutput());
		}
		findingUser.setFirstName(user.getFirstName());
		findingUser.setLastName(user.getLastName());
		findingUser.setEmail(user.getEmail());
		findingUser.setGender(user.getGender());
		findingUser.setPhone(user.getPhone());
		findingUser.setPassword(findingUser.getPassword());

		this.userRepository.save(findingUser);// UPDATE information
		logger.info("User " + findingUser.getUsername() + " updated!");
		return this.mapperUser.map(findingUser);
	}

	@Override
	public void changePassword(ChangePassDTO dto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = this.userRepository.findByUsername(username).orElseThrow(
				() -> new InternalServerErrorException(this.messageSource.getMessage("error.userAuthen", null, null)));
		String newPassword = dto.getNewPassword();
		if (checkIfValidOldPassword(user.getPassword(), dto.getOldPassword())) //Compare OldPassword and User Of Password
		{
			if(!this.validation.passwordValid(newPassword))	//Check Password is strong
				throw new InternalServerErrorException(messageSource.getMessage("error.passwordRegex", null, null));
			user.setPassword(this.passwordEncoder.encode(newPassword));
			this.userRepository.save(user);
		} else {
			throw new InvalidOldPasswordException(this.messageSource.getMessage("error.passwordIncorrect", null, null));
		}
	}

	public ResponeMessage deleteById(String id) {
		User user = this.findByUsername(id);
		user.setStatus(statusService.findByName(Estatus.Disable));
		logger.info(String.format("User %s has been delete.", user.getUsername()));
		this.userRepository.save(user);
		return new ResponeMessage(200,
				String.format(messageSource.getMessage("info.deleteUser", null, null), user.getUsername()));
	}

	@Override
	public ResponeMessage recover(String username) {
		User user = this.findByUsername(username);
		user.setStatus(statusService.findByName(Estatus.Active));
		logger.info(String.format("User %s has been restore", user.getUsername()));
		this.userRepository.save(user);
		return new ResponeMessage(200,
				String.format(messageSource.getMessage("info.recoverUser", null, null), user.getUsername()));
	}

	@Override
	public ResponeMessage sendMailForgotPassword(String email) {
		if (!existsByEmail(email))
			throw new ResourceNotFound("User", "email", email);
		MailResponse mail = new MailResponse();
		mail.setTo(email);//Set email to reset password!  Get User by Email => Change Password
		mail.setTypeMail(TypeMail.ForgotPassword);
		mailService.send(mail);
		return new ResponeMessage(200, "SEND MAIL");
	}

	@Override
	public UserDetailsDTO findById(long id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFound("User", "id", String.valueOf(id)));
		return this.mapperUser.mapToUserDetails(user);
	}

	@Override
	public User save(User user) // SAVE USER
	{

		return this.userRepository.save(user);
	}

	@Override
	public String resetPassword(String email) {
		// GET USER CURRENT
		User user = findByEmail(email);

		String newPassword = randomNewPassword();

		user.setPassword(this.passwordEncoder.encode(newPassword));
		user = this.userRepository.save(user);
		logger.info(String.format("User %s request reset password", user.getUsername()));
		return newPassword;
	}
	

	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email).orElseThrow(() ->
				new InternalServerErrorException(this.messageSource.getMessage("error.emailFormat", null, null)));
	}

	@Override
	public boolean checkIfValidOldPassword(String oldPass, String confirmPass) {
		return passwordEncoder.matches(confirmPass, oldPass);
	}

	@Override
	public List<Object[]> statisticsBySex() {
		return userRepository.statisticsBySex();
	}

	public Long getCountUsernameByYear(String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fromDate = LocalDate.parse(from, formatter);
		LocalDate toDate = LocalDate.parse(to, formatter);
		return userRepository.getCountUsernameByYear(fromDate, toDate);
	}

	public long count() {
		return userRepository.count();
	}

	@Override
	public Long getCount(String from, String to) {
		if (from.isEmpty()) {
			return getCountUsernameByYear(from, to);
		} else {
			return count();
		}
	}

	@Override
	public List<Object[]> statisticsByStatus() {
		return userRepository.statisticsByStatus();
	}

	@Override
	public Long statisticsNewUser() {
		return userRepository.statisticsNewUser();
	}

	@Override
	public List<Object[]> statisticsByRole() {
		return userRepository.statisticsByRole();
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	public String randomNewPassword()// Xu ly viec random 1 password moi
	{
		boolean isStrong = false;
		String newPassword = "";
		while(!isStrong)
		{
			newPassword = RandomString.make(10);
			if(this.validation.passwordValid(newPassword))
				isStrong = true;
		}
		return newPassword;
	}

	@Override
	public User saveAdmin(User user) {
		LocalDate now = LocalDate.now();
		user.setCreateDate(now);
		user.setPassword(encodePass(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public PaginationDTO getUserByUsernameAndPaging(String username, int no, int limit) {
		Pageable page = PageRequest.of(no, limit);
		Page<User> userPage = userRepository.getUserByUserNameAndPaging(username, page);
		List<UserDTO> users = userPage.getContent().stream().map(user->mapperUser.map(user)).collect(Collectors.toList());
		return new PaginationDTO(users,userPage.isFirst(),userPage.isLast(),userPage.getTotalPages(),
				userPage.getTotalElements(),userPage.getSize(),userPage.getNumberOfElements()) ;
	}
	
}
