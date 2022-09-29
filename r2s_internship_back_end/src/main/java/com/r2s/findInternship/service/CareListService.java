package com.r2s.findInternship.service;

import com.r2s.findInternship.dto.CareListDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;

import java.util.List;

public interface CareListService {
    PaginationDTO findAllByUsernamePaging(String username, int no, int limit);

    PaginationDTO findAllByCandidateIdPaging(int candidateId, int no, int limit);

    List<CareListDTO> findAll();

    CareListDTO findById(int id);

    List<CareListDTO> findByUsername(String username);

    CareListDTO findByUsernameAndJobId(String username, int jobId);

    CareListDTO save(CareListDTO careListDTO);

    CareListDTO update(int id, CareListDTO careListDTO);

    ResponeMessage deleteById(int id);
}
