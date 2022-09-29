package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.common.ERole;
import com.r2s.findInternship.common.Estatus;
import com.r2s.findInternship.common.FileUpload;
import com.r2s.findInternship.dto.CandidateCreateDTO;
import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.dto.MajorDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.UserCreationDTO;
import com.r2s.findInternship.entity.Candidate;
import com.r2s.findInternship.entity.Major;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperCandidate;
import com.r2s.findInternship.mapstructmapper.MapperMajor;
import com.r2s.findInternship.mapstructmapper.MapperUser;
import com.r2s.findInternship.repository.CandidateRepository;
import com.r2s.findInternship.service.CandidateService;
import com.r2s.findInternship.service.MajorService;
import com.r2s.findInternship.service.RoleService;
import com.r2s.findInternship.service.StatusService;
import com.r2s.findInternship.service.UserService;
import com.r2s.findInternship.util.UpdateFile;

@Service
public class CandidateServiceImpl implements CandidateService {
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MapperCandidate mapperCandidate;
	@Autowired
	private MapperUser mapperUser;
	@Autowired
	private UpdateFile updateFile;
	@Autowired
	private MajorService majorService;
	@Autowired
	private MapperMajor mapperMajor;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private StatusService statusService;
	@Autowired
	private RoleService roleService;

	public final static Logger logger = LoggerFactory.getLogger("info");

	@Override
	public PaginationDTO findByJobIdPaging(int jobId, int no, int limit) {
		Pageable page = PageRequest.of(no, limit);
		List<Object> candidateList = candidateRepository.findByJobId(jobId, page).toList().stream().map(item -> mapperCandidate.map(item)).collect(Collectors.toList());
		Page<Candidate> candidatePage = candidateRepository.findByJobId(jobId, page);
		return new PaginationDTO(candidateList, candidatePage.isFirst(), candidatePage.isLast(), candidatePage.getTotalPages(),
				candidatePage.getTotalElements(), candidatePage.getSize(), candidatePage.getNumber());
	}

	@Override
	public PaginationDTO findAllPaging(int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> candidateList = candidateRepository.findAll(pageable).toList().stream().map(item -> mapperCandidate.map(item)).collect(Collectors.toList());
		Page<Candidate> candidatePage = candidateRepository.findAll(pageable);
		return new PaginationDTO(candidateList, candidatePage.isFirst(), candidatePage.isLast(), candidatePage.getTotalPages(),
				candidatePage.getTotalElements(), candidatePage.getSize(), candidatePage.getNumber());
	}

	@Override
	public PaginationDTO findAllByMajorPaging(int majorId, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> candidateList = candidateRepository.findByMajorId(majorId, pageable).toList().stream().map(item -> mapperCandidate.map(item)).collect(Collectors.toList());
		Page<Candidate> candidatePage = candidateRepository.findByMajorId(majorId, pageable);
		return new PaginationDTO(candidateList, candidatePage.isFirst(), candidatePage.isLast(), candidatePage.getTotalPages(),
				candidatePage.getTotalElements(), candidatePage.getSize(), candidatePage.getNumber());
	}

	@Override
	public void deleteById(Integer id) {
		CandidateDTO candidateDTO = this.findById(id);
		Candidate candidate = this.mapperCandidate.map(candidateDTO);
		User user = this.userService.findByUsername(candidate.getUser().getUsername());
		user.setStatus(statusService.findByName(Estatus.Disable));
		candidate.setUser(user);
		logger.info(String.format("Delete Candidate with username: %s", user.getUsername()));
		// UPDATE STATUS
		this.candidateRepository.save(candidate);
	}

	@Override
	public boolean exists(CandidateDTO candidateDTO) {
		return candidateRepository.existsById(candidateDTO.getId());
	}

	@Override
	public long count() {
		return candidateRepository.count();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.candidateRepository.existsById(id);
	}

	@Override
	public void flush() {

	}

	@Override
	public CandidateDTO findById(Integer id) {
		Candidate candidate = this.candidateRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Candidate", "Id", String.valueOf(id)));
		return this.mapperCandidate.map(candidate);
	}

	@Override
	public List<CandidateDTO> findAll() {

		return this.candidateRepository.findAll().stream().map(item -> this.mapperCandidate.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public CandidateDTO save(CandidateCreateDTO s) // LUU VOI FRONT END CO CANDIDATE VA USER INFORMATION
	{
		UserCreationDTO userCreationDTO = s.getCreateUser();
		if (userCreationDTO == null)// Khong co user thi throw loi
		{
			throw new InternalServerErrorException(this.messageSource.getMessage("error.userMust", null, null));
		}
		userCreationDTO = userService.handlerValid(s.getCreateUser());
		if (userCreationDTO.getFileAvatar() != null)// TRUONG HOP CO FILE UPLOAD
		{
			FileUpload fileAva = new FileUpload();
			fileAva.setFile(userCreationDTO.getFileAvatar());
			this.updateFile.update(fileAva);
			userCreationDTO.setAvatar(fileAva.getOutput());
		}
		User user = mapperUser.map(userCreationDTO);
		String password = userCreationDTO.getPassword();
		user.setPassword(userService.encodePass(password));
		user.setStatus(this.statusService.findByName(Estatus.Active));
		user.setRole(roleService.findByName(ERole.Cadidate));
		
		Candidate candidate = this.mapperCandidate.map(s);
		candidate.setUser(user);
		if (s.getMajor() != null) {
			Major major = mapperMajor.map(majorService.findById(s.getMajor().getId()));
			candidate.setMajor(major);
		}
		if (s.getFileCV() != null)// UPLOAD FILE CV
		{
			FileUpload file = new FileUpload();
			file.setFile(s.getFileCV());
			this.updateFile.uploadCV(file);
			candidate.setCV(file.getOutput());
		}
		Optional<Candidate> entity = this.candidateRepository.findByUserName(candidate.getUser().getUsername());
		if (entity.isPresent()) {
			candidate.setId(entity.get().getId());
		}
		candidate = this.candidateRepository.save(candidate);
		logger.info(String.format(this.messageSource.getMessage("info.addCandidate", null, null),
				candidate.getUser().getUsername()));
		return this.mapperCandidate.map(candidate);
	}

	@Override
	public CandidateDTO findByUsername(String username) {
		Candidate candidate = this.candidateRepository.findByUserName(username)
				.orElseThrow(() -> new ResourceNotFound("User", "username", username));
		return this.mapperCandidate.map(candidate);
	}

	public Candidate findByUserId(long id) {
		return candidateRepository.findByUserId(id)
				.orElseThrow(() -> new ResourceNotFound("User", "Id", String.valueOf(id)));
	}

	@Override
	public CandidateDTO update(CandidateCreateDTO s) {
		UserCreationDTO userDto = s.getCreateUser();
		if (userDto == null)// check user create, if user not null throw exception
			throw new InternalServerErrorException(messageSource.getMessage("error.usernameNotNull", null, null));
		Candidate candidate = this.findByUserId(userDto.getId());
		if (s.getFileCV() != null) // UPLOAD FILE
		{
			FileUpload fileUpload = new FileUpload();
			fileUpload.setFile(s.getFileCV());
			String urlCV = candidate.getCV();
			if (urlCV != null)
				this.updateFile.deleteFile(urlCV);
			this.updateFile.uploadCV(fileUpload);
			candidate.setCV(fileUpload.getOutput());
		} else {
			candidate.setCV(candidate.getCV());
		}
		MajorDTO majorDto = majorService.findById(s.getMajor().getId());
		candidate.setMajor(mapperMajor.map(majorDto));
		User userEntity = candidate.getUser(); 
		if (userDto.getFileAvatar() != null)// CASE UPLOAD AVATAR
		{

			FileUpload fileUp = new FileUpload();

			fileUp.setFile(userDto.getFileAvatar());
			String url = userEntity.getAvatar();
			if (url != null || !url.equals(""))
				this.updateFile.deleteFile(url);
			this.updateFile.update(fileUp);
			userEntity.setAvatar(fileUp.getOutput());
		} else
			userEntity.setAvatar(userEntity.getAvatar());
		if (!userEntity.getEmail().equals(userDto.getEmail())) {

			if (userService.existsByEmail(userDto.getEmail())) {
				throw new InternalServerErrorException(this.messageSource.getMessage("error.emailExists", null, null));
			}
		}
		System.out.print(userEntity.getAvatar());

		userEntity.setEmail(userDto.getEmail());
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setGender(userDto.getGender());
		userEntity.setPhone(userDto.getPhone());
		candidate.setUser(userEntity);
		candidate = this.candidateRepository.save(candidate);
		logger.info("Update Candidate with username: " + candidate.getUser().getUsername());
		return this.mapperCandidate.map(candidate);
	}

	@Override
	public CandidateCreateDTO readJson(String value, MultipartFile file, MultipartFile fileAvatar) {
		CandidateCreateDTO candidate = null;
		try {
			ObjectMapper ob = new ObjectMapper();
			candidate = new CandidateCreateDTO();
			candidate = ob.readValue(value, CandidateCreateDTO.class);
		} catch (JsonProcessingException ex) // LOG ERROR
		{
			ex.printStackTrace();
		} catch (Exception e) {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
		}
		if (file != null)
			candidate.setFileCV(file);
		if (fileAvatar != null)
			candidate.getCreateUser().setFileAvatar(fileAvatar);
		return candidate;

	}

	@Override
	public List<CandidateDTO> findCandidateByMajorName(int id) {
		this.majorService.findById(id);
		return this.candidateRepository.findByMajorName(id).stream()
				.map(item -> this.mapperCandidate.map(item)).collect(Collectors.toList());
	}

	@Override
	public CandidateDTO findByUserId(int id) {
		return mapperCandidate.map(this.candidateRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFound("Candidate", "id", String.valueOf(id))));
	}

	@Override
	public void SaveWithAddUser(Candidate candidate)// Truong hop add lan dau trong flutter
	{
		this.candidateRepository.save(candidate);
	}

	@Override
	public List<Object[]> statisticsByMajor() {
		return candidateRepository.statisticsByMajor();
	}

}
