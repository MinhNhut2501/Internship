package com.r2s.findInternship.service.impl;

import com.r2s.findInternship.common.Estatus;
import com.r2s.findInternship.dto.*;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.Rate;
import com.r2s.findInternship.exception.ExceptionCustom;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperRate;
import com.r2s.findInternship.mapstructmapper.MapperUser;
import com.r2s.findInternship.repository.RateRepository;
import com.r2s.findInternship.service.CompanyService;
import com.r2s.findInternship.service.RateService;
import com.r2s.findInternship.service.StatusService;
import com.r2s.findInternship.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private MapperRate mapperRate;
    @Autowired
    private MapperUser mapperUser;
    @Autowired
    private StatusService statusService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    public final static Logger logger = LoggerFactory.getLogger("info");

    @Override
    public List<RateDTO> findAll() {
        return this.rateRepository.findAll().stream().map(item -> this.mapperRate.map(item)).collect(Collectors.toList());
    }

    @Override
    public List<RateDTO> findAllActive() {
        return this.rateRepository.findAllActive().stream().map(item -> this.mapperRate.map(item)).collect(Collectors.toList());
    }

    @Override
    public RateDTO findById(int id) {
        return this.mapperRate.map(this.rateRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Rate", "id", String.valueOf(id))));
    }

    @Override
    public RateDTO findByCompanyIdAndUsername(int companyId, String username) {
        return mapperRate.map(this.rateRepository.findByUserAndCompany(username, companyId).orElseThrow(() -> new ResourceNotFound("Rate", "(username, companyId)", "(" + username + ", " + companyId + ")")));
    }

    @Override
    public RateDTO findByCompanyIdAndUsernameToShowClient(int companyId, String username) {
        return this.mapperRate.map(rateRepository.findByUserAndCompanyActive(username, companyId).orElseThrow(() -> new ResourceNotFound("Rate", "(username, company)", "(" + username + ", " + companyId + ")")));
    }

    @Override
    public PaginationDTO findAllByCompanyIdPaging(int companyId, int no, int limit) {
        Pageable page = PageRequest.of(no, limit);
        List<Object> listRate = this.rateRepository.findAllByCompanyIdPagingActive(companyId, page)
                .toList().stream().map(item -> mapperRate.map(item)).collect(Collectors.toList());
        Page<Rate> pageRate = this.rateRepository.findAllByCompanyIdPagingActive(companyId, page);
        return new PaginationDTO(listRate, pageRate.isFirst(), pageRate.isLast(),
                pageRate.getTotalPages(), pageRate.getTotalElements(), pageRate.getSize(), pageRate.getNumber());
    }

    @Override
    public RateDTO save(RateDTO rateDTO) {
        String action = "created rate";
        // determine company id and username is available
        int companyId = rateDTO.getCompany().getId();
        CompanyDTO companyDTO = this.companyService.findById(companyId);
        if (companyDTO.getStatus().getName().equals(Estatus.Disable.name()))
            throw new ExceptionCustom(this.messageSource.getMessage("error.companyStatusDisable", null, null));
        String username = rateDTO.getUser().getUsername();
        UserDTO userDTO = mapperUser.map(this.userService.findByUsername(username));

        Optional<Rate> optionalRate = this.rateRepository.findByUserAndCompany(username, companyId);
        Rate rate;
        int rateId = 0;
        // if present record in database
        if (optionalRate.isPresent()) {
            rate = optionalRate.get();
            // if rate is active then throw an exception
            if (rate.getStatus().getName().equals(Estatus.Active.name()))
                throw new ExceptionCustom(this.messageSource.getMessage("error.commentOnce", null, null));
            action = "set rate active";
            rateId = rate.getId();
        }

        rateDTO.setCompany(companyDTO);
        rateDTO.setUser(userDTO);
        rateDTO.setTitle((rateDTO.getTitle() == null || rateDTO.getTitle().isEmpty()) ? "Đóng góp ý kiến" : rateDTO.getTitle());
        rateDTO.setCreateDate(LocalDate.now());
        rate = mapperRate.map(rateDTO);
        rate.setId(rateId);
        rate.setStatus(this.statusService.findByName(Estatus.Active));
        // save rate
        rate = this.rateRepository.save(rate);
        // write log
        logger.info(String.format("Candidate %s %s with company %s successfully", rateDTO.getUser().getUsername(), action, rateDTO.getCompany().getName()));
        return this.mapperRate.map(rate);
    }

    @Override
    public RateDTO update(int id, RateDTO rateDTO) {
        Rate foundRate = this.rateRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Rate", "id", String.valueOf(id)));
        if (foundRate.getStatus().getName().equals(Estatus.Disable.name()))
            throw new ExceptionCustom(this.messageSource.getMessage("error.updateWhenActive", null, null));
        Rate newRate = this.mapperRate.mapUpdate(foundRate, rateDTO);
        newRate = rateRepository.save(newRate);
        logger.info(String.format("Candidate %s updated the rate with company %s successfully", foundRate.getUser().getUsername(), foundRate.getCompany().getName()));
        return this.mapperRate.map(newRate);
    }

    @Override
    public ResponeMessage deleteById(int id) {
        Rate rate = this.rateRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Rate", "id", String.valueOf(id)));
        rate.setStatus(this.statusService.findByName(Estatus.Disable));
        this.rateRepository.save(rate);
        logger.info(String.format("Candidate %s removed the rate with company %s successfully", rate.getUser().getUsername(), rate.getCompany().getName()));
        return new ResponeMessage(200, String.format(this.messageSource.getMessage("info.deleteRate", null, null), rate.getUser().getUsername(), rate.getCompany().getName()));
    }

    @Override
    public ResponeMessage recover(int id) {
        Rate rate = this.rateRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Rate", "id", String.valueOf(id)));
        rate.setStatus(this.statusService.findByName(Estatus.Active));
        this.rateRepository.save(rate);
        logger.info(String.format("Candidate %s recover the rate with company %s successfully", rate.getUser().getUsername(), rate.getCompany().getName()));
        return new ResponeMessage(200, String.format(this.messageSource.getMessage("info.recoverRate", null, null), rate.getUser().getUsername(), rate.getCompany().getName()));
    }

	@Override
	public PaginationDTO findAllRatePaging(int no, int limit, int companyId) {
		Pageable page = PageRequest.of(no, limit);
		List<Object> rateList = rateRepository.findByCompanyIdPagination(companyId,page).toList().stream().map(item -> mapperRate.map(item)).collect(Collectors.toList());
		Page<Rate> ratePage = rateRepository.findByCompanyIdPagination(companyId,page);
		return new PaginationDTO(rateList,ratePage.isFirst(),ratePage.isLast(),ratePage.getTotalPages(),ratePage.getTotalElements(),
				ratePage.getSize(),ratePage.getNumber());
	}
}
