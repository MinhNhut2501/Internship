package com.r2s.findInternship.service;

import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.RateDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;

import java.util.List;

public interface RateService {
    // USE FOR TEST
    List<RateDTO> findAll();

    List<RateDTO> findAllActive();

    RateDTO findById(int id);

    RateDTO findByCompanyIdAndUsername(int companyId, String username);

    // USE FOR FLUTTER AND WEB
    RateDTO findByCompanyIdAndUsernameToShowClient(int companyId, String username);

    PaginationDTO findAllByCompanyIdPaging(int companyId, int no, int limit);

    RateDTO save(RateDTO rateDTO);

    RateDTO update(int id, RateDTO rateDTO);

    ResponeMessage deleteById(int id);

    ResponeMessage recover(int id);
    
    PaginationDTO findAllRatePaging(int no, int limit, int companyId);
    
    
    
}
