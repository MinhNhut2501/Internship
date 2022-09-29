package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.PaginationDTO;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.CandidateCreateDTO;
import com.r2s.findInternship.dto.CandidateDTO;
import com.r2s.findInternship.entity.Candidate;

public interface CandidateService {
	PaginationDTO findByJobIdPaging(int jobId, int no, int limit);

    PaginationDTO findAllPaging(int no, int limit);

    PaginationDTO findAllByMajorPaging(int majorId, int no, int limit);

    void deleteById(Integer id);

    boolean exists(CandidateDTO candidateDTO);

    long count();

    boolean existsById(Integer id);

    void flush();

    CandidateDTO findById(Integer id);

    List<CandidateDTO> findAll();

    CandidateDTO save(CandidateCreateDTO s);

    CandidateDTO update(CandidateCreateDTO s);

    CandidateCreateDTO readJson(String value, MultipartFile file, MultipartFile fileAvatar);

    List<CandidateDTO> findCandidateByMajorName(int id);

    CandidateDTO findByUserId(int id);

    CandidateDTO findByUsername(String username);

    void SaveWithAddUser(Candidate candidate);

    List<Object[]> statisticsByMajor();

}
