package com.r2s.findInternship.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.r2s.findInternship.dto.*;
import com.r2s.findInternship.dto.show.ResponeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.dto.show.JobDTOShow;
import com.r2s.findInternship.entity.Job;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperJob;
import com.r2s.findInternship.repository.JobRepository;
import com.r2s.findInternship.service.JobPositionService;
import com.r2s.findInternship.service.JobService;
import com.r2s.findInternship.service.MajorService;
import com.r2s.findInternship.service.StatusService;

@Service
public class JobServiceImpl implements JobService {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private MapperJob mapperJob;
	@Autowired
	private StatusService statusService;
	@Autowired
	private JobPositionService jobPositionService;
	@Autowired
	private MajorService majorService;
	private static final Logger logger = LoggerFactory.getLogger("info");
	@Override
	
	// Post job
	public JobDTO save(JobDTO jobDTO) {
		JobPositionDTO jobPositionDTO = this.jobPositionService.findById(jobDTO.getJobposition().getId());
		jobDTO.setJobposition(jobPositionDTO);
		MajorDTO majorDTO = this.majorService.findById(jobDTO.getMajor().getId());
		jobDTO.setMajor(majorDTO);
		
		
		jobDTO.setStatus(statusService.findById(1));
		Job job = this.mapperJob.map(jobDTO);
		job.setCreateDate(LocalDate.now());
		if (jobDTO.getTimeStartStr() != null && jobDTO.getTimeEndStr() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			job.setTimeStart(LocalDate.parse(jobDTO.getTimeStartStr(),formatter));
			job.setTimeEnd(LocalDate.parse(jobDTO.getTimeEndStr(),formatter));
			if (job.getTimeEnd().compareTo(job.getTimeStart()) < 0) {
				throw new InternalServerErrorException("Time Start and Time End is not suitable!");
			}
		}
		job = jobRepository.save(job);
		logger.info("Post job successfully");
		return this.mapperJob.map(job);
	}

	@Override
	public List<JobDTO> findAll() {
		return jobRepository.findAll(Sort.by(Order.by("createDate"))).stream().map(item -> this.mapperJob.map(item)).collect(Collectors.toList());
	}
	
	@Override
	public List<JobDTO> findAllToShowClient() {
		return jobRepository.getJobsActive().stream().map(item -> this.mapperJob.map(item)).collect(Collectors.toList());
	}
	
	@Override
	public JobDTO findById(Integer id) {
		return this.mapperJob.map(jobRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Job","id",String.valueOf(id))));
	}

	@Override
	public JobDTOShow findByIdToShow(Integer id) {
		return this.mapperJob.mapJobShow(jobRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Job","id",String.valueOf(id))));
	}

	
	@Override
	public boolean existsById(Integer id) {
		return jobRepository.existsById(id);
	}

	@Override
	public long count() {
		return jobRepository.count();
	}

	@Override
	public ResponeMessage deleteById(Integer id) {
		if(!this.existsById(id)) throw new ResourceNotFound("Job","id",String.valueOf(id));
		jobRepository.deleteById(id);
		logger.info(String.format("Job with id %s deleted successfully", id));
		return new ResponeMessage(200, messageSource.getMessage("info.deleteJob", null, null));
	}

	@Override
	public PaginationDTO findAllActiveJobPaging(int no, int limit) {
		Pageable page = PageRequest.of(no, limit);
		List<JobDTO> jobList = jobRepository.findAllActivePaging(page).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findAllActivePaging(page);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO searchActiveJobByNameAndProvincePaging(String name, String province, int no, int limit) {
		Pageable page = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByNameAndProvinceActivePaging(name, province, page).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByNameAndProvinceActivePaging(name, province, page);;

		if (name.isEmpty() && province.isEmpty()) {
			jobList = jobRepository.findAllActivePaging(page).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
			jobPage = jobRepository.findAllActivePaging(page);
		}
		if (province.isEmpty()) {
			jobList = jobRepository.findByNameContainingActivePaging(name, page).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
			jobPage = jobRepository.findByNameContainingActivePaging(name, page);
		}

		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO findAllActiveJobByHRIdPaging(int hrId, int no, int limit) {
		Pageable page = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByHrIdActivePaging(hrId, page).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByHrIdActivePaging(hrId, page);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO findAllJobPaging(int no, int limit) {
		Pageable page = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findAll(page).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findAll(page);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO findAllActiveJobByCompanyIdPaging(int companyId, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByCompanyIdActivePaging(companyId, pageable).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByCompanyIdActivePaging(companyId, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO findAllActiveJobByUserIdPaging(long userId, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByUserIdActivePaging(userId, pageable).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByUserIdActivePaging(userId, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}
	
	@Override
	public PaginationDTO findAllJobByUserIdPaging(long userId, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findAllByUserIdPaging(userId, pageable).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findAllByUserIdPaging(userId, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}
	
	@Override
	public PaginationDTO findDisableJobByUserIdPaging(long userId, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByUserIdDisablePaging(userId, pageable).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByUserIdDisablePaging(userId, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO findAllActiveJobByUsernamePaging(String username, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByUsernameActivePaging(username, pageable).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByUsernameActivePaging(username, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO findActiveJobByDistrictAndProvincePaging(String district, String province, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByDistrictAndProvincePaging(district, province, pageable).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByDistrictAndProvincePaging(district, province, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO searchActiveJobByNameAndCandidateApplyPaging(String name, int candidateId, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByNameAndCandidateApplyActivePaging(name, candidateId, pageable).toList()
				.stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByNameAndCandidateApplyActivePaging(name, candidateId, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO filterPaging(String name, String province, List<String> jobTypeList, List<String> jobPositionList, List<String> majorList, String order, int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.filterByKeywords(name, province, jobPositionList, jobTypeList, majorList, order, pageable).toList().stream().map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.filterByKeywords(name, province, jobPositionList, jobTypeList, majorList, order, pageable);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public PaginationDTO searchActiveJobByNameAndCandidateCarePaging(String name, int candidateId, int no, int limit) {
		Pageable page = PageRequest.of(no, limit);
		List<Object> jobList = jobRepository.findByNameAndCandidateCareActivePaging(name, candidateId, page).toList().stream()
				.map(item -> mapperJob.map(item)).collect(Collectors.toList());
		Page<Job> jobPage = jobRepository.findByNameAndCandidateCareActivePaging(name, candidateId, page);
		return new PaginationDTO(jobList, jobPage.isFirst(), jobPage.isLast(), jobPage.getTotalPages(),
				jobPage.getTotalElements(), jobPage.getSize(), jobPage.getNumber());
	}

	@Override
	public JobDTO update(int id, JobDTO dto) {
		Job oldJob = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Job","id",String.valueOf(id)));
		Job newJob = this.mapperJob.map(dto);
		newJob.setId(oldJob.getId());
		newJob = this.jobRepository.save(newJob);
		logger.info(String.format("Job with id %s has been updated successfully", id));
		return this.mapperJob.map(newJob);
	}

	@Override
	public JobDTO changeStatus(int id, JobDTO dto) {
		JobDTO jobDTO = this.findById(id);
		StatusDTO statusDTO = statusService.findById(dto.getStatus().getId());
		jobDTO.setStatus(statusDTO);
		Job newJob = mapperJob.map(jobDTO);
		newJob.setId(id);
		newJob = jobRepository.save(newJob);
		logger.info(String.format("Update status of job with %s successfully", id));
		return mapperJob.map(newJob);
	}

	@Override
	public Long statisticsNewJob() {
		return jobRepository.statisticsNewJob();
	}

	public Long getCountJobByDate(String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fromDate  = LocalDate.parse(from, formatter);
		LocalDate toDate  = LocalDate.parse(to, formatter);
		return jobRepository.getCountJobByDate(fromDate, toDate);
	}

	@Override
	public List<Object[]> statisticsByStatus() {
		return jobRepository.statisticsByStatus();
	}
	@Override
	public Long getCount(String from, String to)
	{
		if(from!= "")
		{
			return getCountJobByDate(from, to);
		}
		else 
		{
			return count();
		}
	}

	@Override
	public List<JobDTO> getJobByCompanyId(Integer id) {
		if(jobRepository.getJobByCompanyId(id).size()==0) {
			throw new ResourceNotFound("Job","id of Company",String.valueOf(id));
		}
		return jobRepository.getJobByCompanyId(id).stream().map(job -> this.mapperJob.map(job)).collect(Collectors.toList());

	}


	@Override
	public List<Object[]> statisticsByJobPosition() {
		return jobRepository.statisticsByJobPosition();
	}

	@Override
	public List<Object[]> statisticsByMajor() {
		return jobRepository.statisticsByMajor();
	}

	@Override
	public List<JobDTO> getJobByUserId(long id) {
		if(jobRepository.getJobByUserId(id).size()==0) {
			throw new ResourceNotFound("Job","id of User",String.valueOf(id));
		}
		return jobRepository.getJobByUserId(id).stream().map(job -> this.mapperJob.map(job)).collect(Collectors.toList());

	}

	@Override
	public List<JobDTO> getJobByUserName(String username) {
		if(jobRepository.getJobByUserName(username).size()==0) {
			throw new ResourceNotFound("Job","name of User",String.valueOf(username));
		}
		return jobRepository.getJobByUserName(username).stream().map(job -> this.mapperJob.map(job)).collect(Collectors.toList());

	}

	

}
