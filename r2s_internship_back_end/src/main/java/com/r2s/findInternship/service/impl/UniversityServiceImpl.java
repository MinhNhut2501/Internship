package com.r2s.findInternship.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.r2s.findInternship.dto.*;
import com.r2s.findInternship.mapstructmapper.MapperLocation;
import com.r2s.findInternship.mapstructmapper.MapperTypeUniversity;
import com.r2s.findInternship.repository.*;
import com.r2s.findInternship.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.common.ERole;
import com.r2s.findInternship.common.Estatus;
import com.r2s.findInternship.common.FileUpload;
import com.r2s.findInternship.common.MailResponse;
import com.r2s.findInternship.common.TypeMail;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.Location;
import com.r2s.findInternship.entity.Partner;
import com.r2s.findInternship.entity.Status;
import com.r2s.findInternship.entity.University;
import com.r2s.findInternship.entity.UniversityLocation;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperUniversity;
import com.r2s.findInternship.util.UpdateFile;

@Service
public class UniversityServiceImpl implements UniversityService {
	@Autowired
	private TypeUniversityService typeUniversityService;
	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private MapperTypeUniversity mapperTypeUniversity;

	@Autowired
	private MapperUniversity mapperUniversity;
	@Autowired
	private MapperLocation mapperLocation;
	@Autowired
	private UserService userService;
	
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private UpdateFile updateFile;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private StatusService statusService;
	@Autowired
	private UniversityLocationRepository universityLocationRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private MailService mailService;

	public final static Logger logger = LoggerFactory.getLogger("info");

	@Override
	public UniversityDTO save(UniversityCreateDTO entityDTO) {
		UniversityCreateDTO entity = checkHandlerValid(entityDTO);
		// save avatar of university
		if (entity.getFile() != null) {
			FileUpload file = new FileUpload();
			file.setFile(entity.getFile());
			this.updateFile.update(file);
			entity.setAvatar(file.getOutput());
		} else
			entity.setAvatar("");
		// mapping data to dto
		University university = this.mapperUniversity.map(entity);
		university.setCreateDate(LocalDate.now());
		university.setStatus(statusService.findByName(Estatus.Active));
		university
				.setTypeUniversity(mapperTypeUniversity.map(typeUniversityService.findById(entity.getType().getId())));
		university = universityRepository.save(university);
		if (!entity.getLocation().isEmpty()) {
			List<UniversityLocation> universityLocations = new ArrayList<>();
			for (LocationDTO item : entity.getLocation()) {
				UniversityLocation universityLocation = new UniversityLocation();
				universityLocation.setUniversity(university);
				// save location
				Location location = mapperLocation.map(item);
				location = locationRepository.save(location);
				universityLocation.setLocation(location);
				// save university location
				universityLocation = this.universityLocationRepository.save(universityLocation);
				universityLocations.add(universityLocation);
			}
			university.setUniversityLocations(universityLocations);
		} else {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.locationMust", null, null));
		}
		university = universityRepository.save(university);
		logger.info(String.format("Admin has create %s successfully with ", university.getName()));
		return this.mapperUniversity.map(university);
	}

	@Override
	public PaginationDTO findActiveJobByProvincePagination(int provinceId, int no, int limit) {
		Pageable page = PageRequest.of(no, limit);// Set page with size 20 member
		List<Object> universityList = universityRepository.findByProvinceId(provinceId, page).stream()
				.map(item -> this.mapperUniversity.map(item)).collect(Collectors.toList());// Chuyen Entity sang dto
		Page<University> universityPage = this.universityRepository.findAll(page);
		return new PaginationDTO(universityList, universityPage.isFirst(), universityPage.isLast(),
				universityPage.getTotalPages(), universityPage.getTotalElements(), universityPage.getSize(),
				universityPage.getNumber());
	}

	@Override
	public List<UniversityDTO> statisticsNewUniversity() {
		return universityRepository.statisticsNewUniversity().stream().map(item -> this.mapperUniversity.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public List<UniversityDTO> findAll() {
		return universityRepository.findAll().stream().map(item -> this.mapperUniversity.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public PaginationDTO findAllPaging(int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> universityList = universityRepository.findAll(pageable).toList().stream()
				.map(item -> mapperUniversity.map(item)).collect(Collectors.toList());
		Page<University> universityPage = universityRepository.findAll(pageable);
		return new PaginationDTO(universityList, universityPage.isFirst(), universityPage.isLast(),
				universityPage.getTotalPages(), universityPage.getTotalElements(), universityPage.getSize(),
				universityPage.getNumber());
	}

	@Override
	public PaginationDTO findByNameContainingPaging(String name, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> universityList = universityRepository.findByNameContaining(name, pageable).toList().stream()
				.map(item -> mapperUniversity.map(item)).collect(Collectors.toList());
		Page<University> universityPage = universityRepository.findByNameContaining(name, pageable);
		return new PaginationDTO(universityList, universityPage.isFirst(), universityPage.isLast(),
				universityPage.getTotalPages(), universityPage.getTotalElements(), universityPage.getSize(),
				universityPage.getNumber());
	}

	@Override
	public PaginationDTO findByShortNameContainingPaging(String shortName, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> universityList = universityRepository.findByShortNameContaining(shortName, pageable).toList()
				.stream().map(item -> mapperUniversity.map(item)).collect(Collectors.toList());
		Page<University> universityPage = universityRepository.findByShortNameContaining(shortName, pageable);
		return new PaginationDTO(universityList, universityPage.isFirst(), universityPage.isLast(),
				universityPage.getTotalPages(), universityPage.getTotalElements(), universityPage.getSize(),
				universityPage.getNumber());
	}

	@Override
	public List<UniversityDTO> findAll(int no) {
		Pageable page = PageRequest.of(no, PaginationDTO.size);// Page: 0 and Member: 20
		return this.universityRepository.findAll(page).toList().stream().map(item -> this.mapperUniversity.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public List<University> findAll(Sort sort) {
		return universityRepository.findAll(sort);
	}

	@Override
	public UniversityDTO findById(Integer id) {
		University entity = universityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("University", "id", String.valueOf(id)));
		UniversityDTO dto = this.mapperUniversity.map(entity);

		return dto;
	}

	@Override
	public boolean existsById(Integer id) {
		return universityRepository.existsById(id);
	}

	@Override
	public long count() {
		return universityRepository.count();
	}

	// PROCESS: SAVE USER -> UNIVERSITY -> PARTNER
	@Override
	public UniversityDTO saveFirst(UniversityCreateDTO dtoClass)// SAVE FOR USER REGISTER
	{
		UniversityCreateDTO dto = this.checkHandlerValid(dtoClass);
		PartnerCreateDTO partnerDTO = dto.getPartnerCreateDTO();
		UserCreationDTO userCreationDTO = partnerDTO.getUserCreationDTO();
		UserDTO userDto = null;
		if (userCreationDTO != null) {
			userCreationDTO.setRole(this.roleService.findByName(ERole.Partner));
			userCreationDTO.setStatus(this.statusService.findByName(Estatus.Disable));
			userDto = this.userService.save(userCreationDTO);// SAVE USERNAME AND PASSWORD
		} else
			throw new InternalServerErrorException("Xu ly khong co USER !");
		// UNIVERSITY
		University entity = mapperUniversity.map(dto);
		// Save partner

		if (dto.getFile() != null) {
			FileUpload file = new FileUpload();
			file.setFile(dto.getFile());
			this.updateFile.update(file);
			entity.setAvatar(file.getOutput());
		} else
			entity.setAvatar("");
		if (dto.getId() == 0) {
			entity.setTypeUniversity(mapperTypeUniversity.map(typeUniversityService.findById(dto.getType().getId())));	
	
			if (universityRepository.existsByEmail(entity.getEmail()))
				throw new InternalServerErrorException(this.messageSource.getMessage("error.emailExists", null, null));
			if (!dto.getLocation().isEmpty()) {
				List<UniversityLocation> universityLocations = new ArrayList<>();
				for (LocationDTO item : dto.getLocation()) {
					UniversityLocation universityLocation = new UniversityLocation();
					universityLocation.setUniversity(entity);
					universityLocation.setLocation(mapperLocation.map(item));
					universityLocations.add(universityLocation);
					this.universityLocationRepository.save(universityLocation);
				}
				entity.setUniversityLocations(universityLocations);
			} else {
				throw new InternalServerErrorException(this.messageSource.getMessage("error.locationMust", null, null));
			}
		
			entity.setStatus(this.statusService.findByName(Estatus.Active)); // not available
			entity.setCreateDate(LocalDate.now());
		}
		universityRepository.save(entity);// SAVE UNIVERSITY

		Partner entityPartner = new Partner();
		entityPartner.setUniversity(entity);
		entityPartner.setPosition(partnerDTO.getPosition());
		entityPartner.setUser(userService.findByUsername(userDto.getUsername()));

		entityPartner = this.partnerRepository.save(entityPartner);
		String fullName = entityPartner.getUser().getFirstName() + " " + entityPartner.getUser().getLastName();
		logger.info(String.format("%s created successful university wit name: %s", fullName, userDto.getUsername()));
		return this.mapperUniversity.map(entity);
	}

	@Override
	public University getById(Integer id) {
		return universityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("University", "id", String.valueOf(id)));
	}

	public UniversityDTO save(UniversityDTO dto) {
		if (dto.getAvatar() != null) {
			// Upload file
		}
		return null;

	}

	public UniversityDTO findByName(String name) {
		University university = universityRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFound("University", "name", name));
		return this.mapperUniversity.map(university);
	}

	@Override
	public List<UniversityDTO> findByNameContaining(String name) {
		if (name.isEmpty())
			return this.findAll();
		return universityRepository.findByNameContaining(name).stream().map(item -> this.mapperUniversity.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public List<UniversityDTO> findByShortNameContaining(String shortName) {
		if (shortName.isEmpty())
			return this.findAll();
		return universityRepository.findByShortNameContaining(shortName).stream()
				.map(item -> this.mapperUniversity.map(item)).collect(Collectors.toList());
	}

	@Override
	public UniversityDTO update(UniversityCreateDTO dtoClass, int id) {
		dtoClass.setId(id);
		UniversityCreateDTO dto = this.checkHandlerValid(dtoClass);
		UniversityDTO universityDTO = this.findById(id);
		University university = this.mapperUniversity.map(dto);
		if (dto.getFile() != null) {
			FileUpload file = new FileUpload();
			file.setFile(dto.getFile());
			this.updateFile.deleteFile(universityDTO.getAvatar());
			this.updateFile.update(file);
			dto.setAvatar(file.getOutput());
		} else {
			dto.setAvatar(universityDTO.getAvatar());
		}
		university.setAvatar(dto.getAvatar());

		university = this.universityRepository.save(university);
		logger.info("Update University with name: " + university.getName());
		return this.mapperUniversity.map(university);
	}

	@Override
	public UniversityCreateDTO readJson(String university, String partner, MultipartFile fileUser,
			MultipartFile fileAvatar) {
		UniversityCreateDTO universityDTO = null;
		PartnerCreateDTO partnerDTO = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			universityDTO = objectMapper.readValue(university, UniversityCreateDTO.class);
		if (partner != "") {
				partnerDTO = objectMapper.readValue(partner, PartnerCreateDTO.class);
			}
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
		}
		if (fileAvatar != null) {
			universityDTO.setFile(fileAvatar);
		}
		if (partnerDTO != null) {
			if (fileUser != null) {
				partnerDTO.getUserCreationDTO().setFileAvatar(fileUser);
			}
			universityDTO.setPartnerCreateDTO(partnerDTO);
		}
		return universityDTO;
	}

	@Override
	public List<UniversityDTO> getAllSort(String field) {
		Sort sort = Sort.by(field);
		return this.universityRepository.findAll(sort).stream().map(item -> this.mapperUniversity.map(item))
				.collect(Collectors.toList());
	}

	public ResponeMessage deleteById(int id) {
		University u = this.universityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("University", "id", String.valueOf(id)));
		u.setStatus(this.statusService.findByName(Estatus.Disable));
		logger.info(String.format("%s deleted.", u.getName()));
		this.universityRepository.save(u);
		return new ResponeMessage(200,
				String.format(this.messageSource.getMessage("info.deleteUniversity", null, null), u.getName()));

	}

	public UniversityCreateDTO checkHandlerValid(UniversityCreateDTO dto) {
		
		Map<String, String> maps = new HashMap<String, String>();
		// nhập vào thông tin thì không gửi kèm id, còn có id thì không gửi thêm thông tin khác
		if (dto.getId() != 0) {
			Optional<University> u = this.universityRepository.findById(dto.getId());
			University entity = u.get();
			dto =    mapperUniversity.mapToCreateDTO(entity);
		
//			if (!dto.getShortName().equals(entity.getShortName())) {
//				if (this.universityRepository.existsByShortName(dto.getShortName()))
//					maps.put("Short Name", messageSource.getMessage("error.UniversityExistsShortName", null, null));
//			}
//			if (!dto.getWebsite().equals(entity.getWebsite())) {
//				if (this.universityRepository.existsByWebsite(dto.getWebsite()))
//					maps.put("Website", messageSource.getMessage("error.UniversityExistsWebsite", null, null));
//			}
//			if (!dto.getEmail().equals(entity.getEmail())) {
//				if (this.universityRepository.existsByEmail(dto.getEmail())) {
//					maps.put("Email", messageSource.getMessage("error.UniversityExistsEmail", null, null));
//				}
//			}
//			if (!dto.getName().equals(entity.getName())) {
//				if (this.universityRepository.existsByName(dto.getName())) {
//					maps.put("Name", messageSource.getMessage("error.UniversityExistsName", null, null));
//				}
//			}
			//-----------------------
//			dto.setPhone(entity.getPhone());
//			dto.setWebsite(entity.getWebsite());
//			dto.setDescription(entity.getDescription());
//			dto.setShortName(entity.getShortName());
//			dto.setEmail(entity.getEmail());
//			dto.setName(entity.getName()); 
//			dto.setType(mapperTypeUniversity.map(entity.getTypeUniversity()));

		} else {
			if (this.universityRepository.existsByShortName(dto.getShortName()))
				maps.put("Short Name", messageSource.getMessage("error.UniversityExistsShortName", null, null));
			if (this.universityRepository.existsByWebsite(dto.getWebsite()))
				maps.put("Website", messageSource.getMessage("error.UniversityExistsWebsite", null, null));
			if (this.universityRepository.existsByName(dto.getName())) {
				maps.put("Name", messageSource.getMessage("error.UniversityExistsName", null, null));
			}
			if (this.universityRepository.existsByEmail(dto.getEmail()))
				maps.put("Email", messageSource.getMessage("error.UniversityExistsEmail", null, null));
		}
		if (maps.size() > 0)
			throw new InternalServerErrorException(maps);
		return dto;
	}

	@Override
	public void recover(int id) {
		University u = universityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("University", "id", String.valueOf(id)));
		u.setStatus(statusService.findByName(Estatus.Active));
		logger.info(String.format("%s has been restored.", u.getName()));
		this.universityRepository.save(u);
	}

	public Long getCountUsernameByDate(String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fromDate = LocalDate.parse(from, formatter);
		LocalDate toDate = LocalDate.parse(to, formatter);
		return universityRepository.getCountUsernameByDate(fromDate, toDate);
	}

	@Override
	public Long getCount(String from, String to) {
		if (!from.isEmpty()) {
			return getCountUsernameByDate(from, to);
		} else {
			return count();
		}
	}

	@Override
	public List<Object[]> statisticsByStatus() {
		return universityRepository.statisticsByStatus();
	}

	public void active(int id) {
		University entity = this.universityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("University", "id", String.valueOf(id)));
		Status status = this.statusService.findByName(Estatus.Active);
		entity.setStatus(status);
		this.universityRepository.save(entity);
		MailResponse mailResponse = new MailResponse();
		mailResponse.setTo(entity.getEmail());
		mailResponse.setTypeMail(TypeMail.ActiveUniversity);
		mailService.send(mailResponse);
	}

}
