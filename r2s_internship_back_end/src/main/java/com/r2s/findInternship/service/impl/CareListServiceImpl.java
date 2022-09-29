package com.r2s.findInternship.service.impl;

import com.r2s.findInternship.dto.CareListDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.Candidate;
import com.r2s.findInternship.entity.CareList;
import com.r2s.findInternship.entity.Job;
import com.r2s.findInternship.exception.ExceptionCustom;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperCandidate;
import com.r2s.findInternship.mapstructmapper.MapperCareList;
import com.r2s.findInternship.mapstructmapper.MapperJob;
import com.r2s.findInternship.repository.CandidateRepository;
import com.r2s.findInternship.repository.CareListRepository;
import com.r2s.findInternship.repository.JobRepository;
import com.r2s.findInternship.service.CareListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareListServiceImpl implements CareListService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MapperCandidate mapperCandidate;
    @Autowired
    private MapperJob mapperJob;
    @Autowired
    private MapperCareList mapperCareList;
    @Autowired
    private CareListRepository careListRepository;
    public final static Logger logger = LoggerFactory.getLogger("info");

    @Override
    public List<CareListDTO> findAll() {
        return this.careListRepository.findAll().stream().map(item -> this.mapperCareList.map(item)).collect(Collectors.toList());
    }

    @Override
    public CareListDTO findById(int id) {
        return this.mapperCareList.map(this.careListRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Care", "id", String.valueOf(id))));
    }

    @Override
    public List<CareListDTO> findByUsername(String username) {
        return this.careListRepository.findAllByUsername(username).stream().map(item -> this.mapperCareList.map(item)).collect(Collectors.toList());
    }

    @Override
    public CareListDTO findByUsernameAndJobId(String username, int jobId) {
        return this.mapperCareList.map(this.careListRepository.findByUsernameAndJobId(username, jobId).orElseThrow(() -> new ResourceNotFound("CareList", "(username, jobId)", "(" + username + ", " + jobId + ")")));
    }

    @Override
    public PaginationDTO findAllByUsernamePaging(String username, int no, int limit) {
        Pageable page = PageRequest.of(no, limit);
        List<Object> listCare = this.careListRepository.findAllByUsername(username, page).toList().stream().map(item -> this.mapperCareList.map(item)).collect(Collectors.toList());
        Page<CareList> pageCare = this.careListRepository.findAllByUsername(username, page);
        return new PaginationDTO(listCare, pageCare.isFirst(), pageCare.isLast(),
                pageCare.getTotalPages(), pageCare.getTotalElements(), pageCare.getSize(), pageCare.getNumber());
    }

    @Override
    public PaginationDTO findAllByCandidateIdPaging(int candidateId, int no, int limit) {
        Pageable page = PageRequest.of(no, limit);
        List<Object> listCare = this.careListRepository.findAllByCandidateId(candidateId, page).toList().stream().map(item -> this.mapperCareList.map(item)).collect(Collectors.toList());
        Page<CareList> pageCare = this.careListRepository.findAllByCandidateId(candidateId, page);
        return new PaginationDTO(listCare, pageCare.isFirst(), pageCare.isLast(),
                pageCare.getTotalPages(), pageCare.getTotalElements(), pageCare.getSize(), pageCare.getNumber());
    }

    @Override
    public CareListDTO save(CareListDTO careListDTO) {
        int candidateId = careListDTO.getCandidateCare().getId();
        int jobId = careListDTO.getJobCare().getId();
        // verify that candidate and job in database
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new ResourceNotFound("Candidate", "id", String.valueOf(candidateId)));
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFound("Job", "id", String.valueOf(jobId)));
        // set candidate and job
        careListDTO.setCandidateCare(mapperCandidate.map(candidate));
        careListDTO.setJobCare(mapperJob.map(job));
        // if candidate has care the job, but want to care again, then throw error
        boolean isExistByUsernameAndCompanyId = this.careListRepository.existsByUsernameAndJobId(candidate.getUser().getUsername(), jobId);

        if (isExistByUsernameAndCompanyId)
            throw new ExceptionCustom(this.messageSource.getMessage("error.careOnce", null, null));
        CareList careList = mapperCareList.map(careListDTO);
        careList.setCreateDate(LocalDate.now());
        careList = careListRepository.save(careList);

        logger.info(String.format("Candidate %s add job %s to care list successfully!", careList.getCandidateCare().getUser().getUsername(), careList.getJobCare().getName()));
        return this.mapperCareList.map(careList);
    }

    @Override
    public CareListDTO update(int id, CareListDTO careListDTO) {
        CareList careList = this.careListRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Care list", "id", String.valueOf(id)));
        careList.setId(id);
        careList.setNote(careListDTO.getNote());
        careList.setCreateDate(LocalDate.now());
        careList = this.careListRepository.save(careList);
        logger.info(String.format("Candidate %s update note about job %s successfully!", careList.getCandidateCare().getUser().getUsername(), careList.getJobCare().getName()));
        return this.mapperCareList.map(careList);
    }

    @Override
    public ResponeMessage deleteById(int id) {
        CareList careList = this.careListRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Care list", "id", String.valueOf(id)));
        this.careListRepository.deleteById(id);
        logger.info(String.format("Candidate %s delete a note successfully!", careList.getCandidateCare().getUser().getUsername()));
        return new ResponeMessage(200, String.format(this.messageSource.getMessage("info.deleteCareList", null, null), careList.getCandidateCare().getUser().getUsername()));
    }
}