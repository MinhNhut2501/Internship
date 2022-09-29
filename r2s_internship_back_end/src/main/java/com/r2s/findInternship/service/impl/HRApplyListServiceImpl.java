package com.r2s.findInternship.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.r2s.findInternship.common.Estatus;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.common.MailResponse;
import com.r2s.findInternship.common.TypeMail;
import com.r2s.findInternship.dto.HRApplyListDTO;
import com.r2s.findInternship.entity.HRApplyList;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperHRApplyList;
import com.r2s.findInternship.repository.HRApplyListRepository;
import com.r2s.findInternship.service.HRApplyListService;
import com.r2s.findInternship.service.MailService;

@Service
public class HRApplyListServiceImpl implements HRApplyListService{
	@Autowired
	private StatusService statusService;

	@Autowired
	HRApplyListRepository hrApplyListRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MapperHRApplyList mapper;
	@Autowired
	private MailService mailService;
	public final static Logger logger = LoggerFactory.getLogger("info");
	@Override
	public HRApplyListDTO findById(Integer id) {
		return this.mapper.map(this.getById(id));
	}

	@Override
	public List<HRApplyListDTO> findAll() {
		return hrApplyListRepository.findAll(Sort.by(Order.by("date"))).stream().map(hrAplylist -> this.mapper.map(hrAplylist)).collect(Collectors.toList());
	}

	@Override
	public PaginationDTO findAllPagingLatest(int no, int limit) {
		Sort sort = Sort.by("date").descending();
		Pageable pageable = PageRequest.of(no, limit, sort);
		List<Object> hrApplyList = hrApplyListRepository.findAll(pageable).toList().stream().map(item -> mapper.map(item)).collect(Collectors.toList());
		Page<HRApplyList> hrApplyPage = hrApplyListRepository.findAll(pageable);
		return new PaginationDTO(hrApplyList, hrApplyPage.isFirst(), hrApplyPage.isLast(), hrApplyPage.getTotalPages(),
				hrApplyPage.getTotalElements(), hrApplyPage.getSize(), hrApplyPage.getNumber());
	}

	@Override
	public PaginationDTO findAllActivePaging(int no, int limit) {
		Pageable pageable = PageRequest.of(no, limit);
		List<Object> hrApplyList = hrApplyListRepository.findAllActive(pageable).toList().stream().map(item -> mapper.map(item)).collect(Collectors.toList());
		Page<HRApplyList> hrApplyListPage = hrApplyListRepository.findAllActive(pageable);
		return new PaginationDTO(hrApplyList, hrApplyListPage.isFirst(), hrApplyListPage.isLast(), hrApplyListPage.getTotalPages(),
				hrApplyListPage.getTotalElements(), hrApplyListPage.getSize(), hrApplyListPage.getNumber());
	}

	@Override
	public HRApplyListDTO save(HRApplyListDTO dto) {
		dto.setDate(LocalDate.now());
		HRApplyList entity = this.mapper.map(dto);
		//SEND MAIL FOR PARTNER
		MailResponse mailResponse = new MailResponse();
		mailResponse.setCv(entity.getDemandUni().getStudents());
		mailResponse.setTo(entity.getHr().getUser().getEmail());
		mailResponse.setTypeMail(TypeMail.HRApply);
		String message = this.messageSource.getMessage("info.addHRApply", null,null);
		mailResponse.setSubject(String.format(message, 
				entity.getHr().getUser().getFirstName() + " " + entity.getHr().getUser().getLastName(),
				entity.getDemandUni().getName()));
		mailResponse.setNamePost(entity.getDemandUni().getName());
		mailResponse.setNameRecieve(entity.getHr().getUser().getFirstName() + " " + entity.getHr().getUser().getLastName());
		this.mailService.send(mailResponse);

		entity.setStatus(statusService.findByName(Estatus.Active));
		entity = hrApplyListRepository.save(entity);
		logger.info(String.format(this.messageSource.getMessage("info.addHRApply", null,null), "TEN DEMAN","TEN HR"));
		return this.mapper.map(entity);
	}

	@Override
	public HRApplyListDTO update(int id, HRApplyListDTO dto) {
		HRApplyList entity = this.getById(id);
		dto.setId(id);
		if(dto.getDate()== null) {
			dto.setDate(entity.getDate());
		}
		entity = mapper.map(dto);
		hrApplyListRepository.save(entity);
		return mapper.map(entity);
	}

	@Override
	public HRApplyList getById(Integer id) {
		return hrApplyListRepository.findById(id).orElseThrow(()-> new ResourceNotFound("HRApplyList","id",String.valueOf(id)));
	}

	@Override
	public boolean deleteById(Integer id) {
		try {
			HRApplyList entity = this.getById(id);
			hrApplyListRepository.save(entity);
		} catch (Exception e) {
			throw new ResourceNotFound("HRApplyList","id",String.valueOf(id));
		}
		return true;
	}

	@Override
	public ResponeMessage delete(int id) {
		HRApplyListDTO foundHRApply = this.findById(id);
		foundHRApply.setId(id);
		foundHRApply.setStatus(statusService.findByName(Estatus.Disable));
		HRApplyList hrApplyList = mapper.map(foundHRApply);
		hrApplyListRepository.save(hrApplyList);
		String fullName = foundHRApply.getHr().getUser().getLastName() + " " + foundHRApply.getHr().getUser().getFirstName();
		logger.info("HR " + fullName + " has been unapply demand " + foundHRApply.getDemandUni().getName() + " successfully!");
		return new ResponeMessage(200, String.format(this.messageSource.getMessage("info.deleteHRApplyList", null, null), fullName, foundHRApply.getDemandUni().getName()));
	}
}
