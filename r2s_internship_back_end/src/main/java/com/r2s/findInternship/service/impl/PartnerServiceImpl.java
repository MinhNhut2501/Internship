package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.dto.CandidateCreateDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.PartnerCreateDTO;
import com.r2s.findInternship.dto.PartnerDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.dto.UserCreationDTO;
import com.r2s.findInternship.dto.show.PartnerUniversityShow;
import com.r2s.findInternship.entity.Partner;
import com.r2s.findInternship.entity.User;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperPartner;
import com.r2s.findInternship.repository.PartnerRepository;
import com.r2s.findInternship.service.PartnerService;
import com.r2s.findInternship.service.UniversityService;
import com.r2s.findInternship.service.UserService;
@Service
public class PartnerServiceImpl implements PartnerService {
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MapperPartner mapperPartner;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UniversityService universityService;

	@Override
	public PartnerDTO save(PartnerDTO entity) {
		if (!userService.existsByUsername(entity.getUser().getUsername())) {
			throw new InternalServerErrorException(messageSource.getMessage("error.usernameIncorrect", null, null));
		}

		Partner p = this.mapperPartner.map(entity);

		p.setUniversity(universityService.getById(entity.getUniversityDTO().getId()));
		return this.mapperPartner.map(this.partnerRepository.save(p));
	}

	@Override
	public List<PartnerDTO> findAll() {
		return partnerRepository.findAll().stream().map(item -> this.mapperPartner.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public PaginationDTO findAllPaging(int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> partnerList = partnerRepository.findAll(pageable).toList().stream().map(item -> mapperPartner.map(item)).collect(Collectors.toList());
		Page<Partner> partnerPage = partnerRepository.findAll(pageable);
		return new PaginationDTO(partnerList, partnerPage.isFirst(), partnerPage.isLast(), partnerPage.getTotalPages(),
				partnerPage.getTotalElements(), partnerPage.getSize(), partnerPage.getNumber());
	}

	@Override
	public PaginationDTO findByUniversityIdPaging(int universityId, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> partnerList = partnerRepository.findByUniversityId(universityId, pageable).toList().stream().map(item -> mapperPartner.map(item)).collect(Collectors.toList());
		Page<Partner> partnerPage = partnerRepository.findByUniversityId(universityId, pageable);
		return new PaginationDTO(partnerList, partnerPage.isFirst(), partnerPage.isLast(), partnerPage.getTotalPages(),
				partnerPage.getTotalElements(), partnerPage.getSize(), partnerPage.getNumber());
	}

	@Override
	public long count() {
		return partnerRepository.count();
	}

	@Override
	public PartnerDTO update(PartnerDTO dto, int id) {
		if (!this.partnerRepository.existsById(id)) {
			throw new ResourceNotFound("Partner", "id", String.valueOf(id));
		}
		if (!userService.existsByUsername(dto.getUser().getUsername())) {
			throw new InternalServerErrorException(messageSource.getMessage("error.usernameIncorrect", null, null));
		}

		Partner p = this.mapperPartner.map(dto);

		p.setUniversity(universityService.getById(dto.getUniversityDTO().getId()));
		p.setId(id);
		return this.mapperPartner.map(this.partnerRepository.save(p));
	}

	@Override
	public List<PartnerUniversityShow> getPartnersByUniversity(int id) {
		UniversityDTO dto = this.universityService.findById(id);
		return partnerRepository.findByUniversityId(dto.getId()).stream()
				.map(item -> this.mapperPartner.mapToUniversityShow(item)).collect(Collectors.toList());
	}

	@Override
	public Partner findByUserId(long id) {
		Partner partner = this.partnerRepository.findByUsername(id)
				.orElseThrow(() -> new ResourceNotFound("Partner", "id", String.valueOf(id)));
		return partner;
	}

	@Override
	public PartnerDTO findById(Integer id) {
		Partner entity = partnerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Partner", "id", String.valueOf(id)));
		PartnerDTO dto = this.mapperPartner.map(entity);
		return dto;
	}

	@Override
	public PartnerDTO updateUser(PartnerCreateDTO partnerCreateDTO) {
		UserCreationDTO userCreationDTO = partnerCreateDTO.getUserCreationDTO();
		if(userCreationDTO == null) {
			throw new InternalServerErrorException(messageSource.getMessage("error.usernameNotNull", null, null));
		}	
		Partner partner = partnerRepository.findByPartnerId(partnerCreateDTO.getId());
		partner.setPosition(partnerCreateDTO.getPosition());
		User userEntity = partner.getUser();
		userEntity.setFirstName(userCreationDTO.getFirstName());
		userEntity.setLastName(userCreationDTO.getLastName());
		userEntity.setGender(userCreationDTO.getGender());
		userEntity.setPhone(userCreationDTO.getPhone());
		userEntity.setEmail(userCreationDTO.getEmail());
		partner.setUser(userEntity);
		partner = this.partnerRepository.save(partner);		
		return this.mapperPartner.map(partner);
	}

	@Override
	public PartnerCreateDTO readJson(String value) {
		PartnerCreateDTO partner = null;
		try {
			ObjectMapper ob = new ObjectMapper();
			partner = new PartnerCreateDTO();
			partner = ob.readValue(value, PartnerCreateDTO.class);
		} catch (JsonProcessingException ex) // LOG ERROR
		{
			ex.printStackTrace();
		} catch (Exception e) {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
		}
		return partner;
	}

}
