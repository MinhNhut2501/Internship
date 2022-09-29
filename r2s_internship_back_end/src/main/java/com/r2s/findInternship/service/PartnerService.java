package com.r2s.findInternship.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.CandidateCreateDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.PartnerCreateDTO;
import com.r2s.findInternship.dto.PartnerDTO;
import com.r2s.findInternship.dto.show.PartnerUniversityShow;
import com.r2s.findInternship.entity.Partner;

public interface PartnerService {

    long count();

    List<PartnerDTO> findAll();

    PaginationDTO findAllPaging(int no, int limit);

    PaginationDTO findByUniversityIdPaging(int universityId, int no, int limit);

    PartnerDTO save(PartnerDTO entity);

    PartnerDTO update(PartnerDTO dto, int id);


    List<PartnerUniversityShow> getPartnersByUniversity(int id);


    Partner findByUserId(long id);


    PartnerDTO findById(Integer id);
    
    PartnerDTO updateUser(PartnerCreateDTO a);
    
    PartnerCreateDTO readJson(String value);


}
