package com.r2s.findInternship.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.common.FileUpload;
import com.r2s.findInternship.dto.DemandUniDTO;
import com.r2s.findInternship.dto.DemandUniShowDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.entity.DemandUni;
import com.r2s.findInternship.entity.JobPosition;
import com.r2s.findInternship.entity.JobType;
import com.r2s.findInternship.entity.Major;
import com.r2s.findInternship.entity.Partner;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperDemandUni;
import com.r2s.findInternship.repository.DemandUniRepository;
import com.r2s.findInternship.repository.JobPositionRepository;
import com.r2s.findInternship.repository.JobTypeRepository;
import com.r2s.findInternship.repository.MajorRepository;
import com.r2s.findInternship.repository.PartnerRepository;
import com.r2s.findInternship.service.DemandUniService;
import com.r2s.findInternship.service.JobTypeService;
import com.r2s.findInternship.service.UniversityService;
import com.r2s.findInternship.util.UpdateFile;

@Service
public class DemandUniServiceImpl implements DemandUniService {
	@Autowired
	private DemandUniRepository demandUniRepository;
	@Autowired
	private MajorRepository majorRepository;
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private JobPositionRepository jobPositionRepository;
	@Autowired
	private MapperDemandUni mapperDemandUni;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UniversityService universityService;
	@Autowired 
	private JobTypeService jobTypeService;
	@Autowired
	private JobTypeRepository jobTypeRepository;
	@Autowired
	private UpdateFile updateFile;
	public final static Logger logger = LoggerFactory.getLogger("info");
	public final static Logger loggerWarn = LoggerFactory.getLogger("warn");
	@Override
	public DemandUniShowDTO save(DemandUniDTO dto) {
		DemandUni entity = mapperDemandUni.map(dto);
		
		entity.setCreateDate(LocalDate.now());
		if (dto.getEndStr() != null && dto.getStartStr() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			entity.setStart(LocalDate.parse(dto.getStartStr(), formatter));
			entity.setEnd(LocalDate.parse(dto.getEndStr(), formatter));
			if (entity.getEnd().compareTo(entity.getStart()) < 0) {
				throw new InternalServerErrorException(this.messageSource.getMessage("error.Date", null, null));
			}
		}
		entity.setStatus(true);
		entity = setPartnerMajorJobPosition(entity, dto);
		entity = setNameFile(entity, dto);//check file
		entity = setJobType(entity, dto); // set jobtype
		DemandUni savedDemand = demandUniRepository.save(entity);
		DemandUniShowDTO dtoShow = new DemandUniShowDTO();
		try
		{
			dtoShow = this.mapperDemandUni.mapToShow(savedDemand);	
		}
		catch (Exception e) {
			System.out.println(entity);
		}
		logger.info("Admin has create demand university with name " + savedDemand.getPartner().getUniversity().getName());
		return dtoShow;
	}

	@Override
	public List<DemandUniShowDTO> findAll() {

		return this.demandUniRepository.findAll().stream().map(item -> this.mapperDemandUni.mapToShow(item))
				.collect(Collectors.toList());
	}

	@Override
	public PaginationDTO findAllPagingLatest(int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> demandUniList = demandUniRepository.findAll(pageable).toList().stream().map(item -> mapperDemandUni.mapToShow(item)).collect(Collectors.toList());
		Page<DemandUni> demandUniPage = demandUniRepository.findAll(pageable);
		return new PaginationDTO(demandUniList, demandUniPage.isFirst(), demandUniPage.isLast(), demandUniPage.getTotalPages(),
				demandUniPage.getTotalElements(), demandUniPage.getSize(), demandUniPage.getNumber());
	}

	@Override
	public PaginationDTO searchByNamePagingLatest(String name, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> demandUniList = this.demandUniRepository.findAllByNameContaining(name, pageable).toList().stream().map(item -> mapperDemandUni.mapToShow(item)).collect(Collectors.toList());
		Page<DemandUni> demandUniPage = this.demandUniRepository.findAllByNameContaining(name, pageable);
		return new PaginationDTO(demandUniList, demandUniPage.isFirst(), demandUniPage.isLast(),
				demandUniPage.getTotalPages(), demandUniPage.getTotalElements(), demandUniPage.getSize(), demandUniPage.getNumber());
	}

	@Override
	public DemandUniDTO readJson(String content, MultipartFile file) {
		DemandUniDTO demand = null;
		try {
			ObjectMapper ob = new ObjectMapper();
			demand = new DemandUniDTO();
			demand = ob.readValue(content, DemandUniDTO.class);
		} catch (IOException e) {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
		}
		if (file != null)
			demand.setFile(file);
		else 
			throw new InternalServerErrorException(this.messageSource.getMessage("error.FileNull", null, null));
		return demand;
	}

	@Override
	public DemandUniShowDTO findById(int id) {
		return this.mapperDemandUni.mapToShow(this.demandUniRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Demand", "id", String.valueOf(id))));
	}

	@Override
	public DemandUniShowDTO update(DemandUniDTO dto, int id) {
		DemandUni entity = mapperDemandUni.map(dto);
		// CHECK PARTNER
		entity = setPartnerMajorJobPosition(entity, dto);
		entity = setNameFile(entity, dto);
		entity = setJobType(entity, dto);
		entity.setId(id);
		entity.setUpdateDate(LocalDate.now());
		entity.setStatus(dto.isStatus());
		if (dto.getEndStr() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			entity.setEnd(LocalDate.parse(dto.getEndStr(), formatter));
		}
		if (entity.getEnd().compareTo(entity.getUpdateDate()) < 0) {
			throw new InternalServerErrorException(this.messageSource.getMessage("error.Date", null, null));
		}
		logger.info("Demand university updated by " + entity.getPartner().getUser().getUsername());
		return this.mapperDemandUni.mapToShow(this.demandUniRepository.save(entity));
	}

	private DemandUni setJobType(DemandUni entity, DemandUniDTO dto) {
		JobType jobType = jobTypeRepository.findById(dto.getJobType().getId())
				.orElseThrow(() -> new ResourceNotFound("JobType", "id", String.valueOf(dto.getJobType().getId())));
		entity.setJobType(jobType);
		return entity;
	}

	private DemandUni setPartnerMajorJobPosition(DemandUni entity, DemandUniDTO dto)// SET MAJOR AND PARTNER AND
																					// JOBPOSITION
	{
		Major major = majorRepository.findById(dto.getMajor().getId())
				.orElseThrow(() -> new ResourceNotFound("Major", "id", String.valueOf(dto.getMajor().getId())));
		entity.setMajor(major);
		Partner partner = partnerRepository.findById(dto.getPartner().getId())
				.orElseThrow(() -> new ResourceNotFound("Partner", "id", String.valueOf(dto.getMajor().getId())));
		entity.setPartner(partner);
		JobPosition jobPosition = jobPositionRepository.findById(dto.getPosition().getId())
				.orElseThrow(() -> new ResourceNotFound("JobPosition", "id", String.valueOf(dto.getPosition().getId())));
		entity.setPosition(jobPosition);
		return entity;
	}

	private DemandUni setNameFile(DemandUni entity, DemandUniDTO dto) // UPLOAD FILE
	{
		if (dto.getFile() != null) {
			String originFile = dto.getFile().getOriginalFilename();
			String attribute = originFile.substring(originFile.lastIndexOf('.') + 1);
			if (!attribute.equals("xlsx"))//CHECK FILE 
			{
				throw new InternalServerErrorException(messageSource.getMessage("error.File", null, null));
			}
			FileUpload file = new FileUpload();
			file.setFile(dto.getFile());
			this.updateFile.deleteFile(entity.getStudents());
			this.updateFile.uploadExcel(file);
			entity.setStudents(file.getOutput());
		}
		return entity;
	}

	@Override
	public void delete(int id) {
		DemandUni entity = this.demandUniRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Demand", "id", String.valueOf(id)));
		entity.setStatus(false);
		loggerWarn.warn("Demand university " + entity.getName() + " deleted" );
	}

	@Override
	public PaginationDTO findByUniversityIdPaging(int id, int no, int limit) {
		boolean isUniversity = this.universityService.existsById(id);
		if(!isUniversity)
			throw new ResourceNotFound("University", "id", String.valueOf(id));
		Pageable page = PageRequest.of(no, limit);
		List<Object> listUser = this.demandUniRepository.findAllByUniversityId(id, page).
				getContent().stream().map(item -> this.mapperDemandUni.mapToShow(item)).collect(Collectors.toList());
		Page<DemandUni> pageUser = this.demandUniRepository.findAllByUniversityId(id, page);
		return new PaginationDTO(listUser, pageUser.isFirst(), pageUser.isLast(),
				pageUser.getTotalPages(), pageUser.getTotalElements(), pageUser.getSize(), pageUser.getNumber());
	}
	
	private Set<JobType> checkJobTypes(Set<JobType> jobTypes)
	{
		Set<JobType> list = new HashSet<JobType>();
		for (JobType item : jobTypes) {
			JobType jobdto = this.jobTypeRepository.findById(item.getId()).orElseThrow(() -> new ResourceNotFound("JobType", "id", String.valueOf(item.getId())));
			list.add(jobdto);
		}
		return list;
	}
}
