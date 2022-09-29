package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.common.Estatus;
import com.r2s.findInternship.dto.StatusDTO;
import com.r2s.findInternship.entity.Status;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperStatus;
import com.r2s.findInternship.repository.StatusRepository;
import com.r2s.findInternship.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private MapperStatus mapperStatus;

	@Override
	public boolean existsById(int id) {
		return statusRepository.existsById(id);
	}

	@Override
	public StatusDTO save(StatusDTO status) {
		Status newEntity = this.mapperStatus.map(status);
		this.statusRepository.save(newEntity);
		StatusDTO dto = this.mapperStatus.map(newEntity);
		return dto;
	}

	@Override
	public List<StatusDTO> findAll() {
		return this.statusRepository.findAll().stream().map(item -> this.mapperStatus.map(item))
				.collect(Collectors.toList());
	}

	@Override
	public StatusDTO findById(Integer id) {
		Status entity = this.statusRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Status", "id", String.valueOf(id)));
		return this.mapperStatus.map(entity);
	}

	@Override
	public Status findByName(Estatus s) {
		Status status = null;
		switch (s) {
		case Active:
			status = this.statusRepository.findById(1)
					.orElseThrow(() -> new ResourceNotFound("Status", "name", "Not Available"));
			break;
		
		case Deleted: status = this.statusRepository.findById(2)
		.orElseThrow(() -> new ResourceNotFound("Status", "name", "Not Available"));
		break;
		
		case Lock:
			status = this.statusRepository.findById(3)
					.orElseThrow(() -> new ResourceNotFound("Status", "name", "Not Available"));
			break;

		case Disable: 
			status = this.statusRepository.findById(4)
					.orElseThrow(() -> new ResourceNotFound("Status", "name", "Not Available"));
			break;
			
		case Pending: 
			status = this.statusRepository.findById(5)
				.orElseThrow(() -> new ResourceNotFound("Status", "name", "Not Available"));
			break;
			
		default:
			status = this.statusRepository.findById(4)
					.orElseThrow(() -> new ResourceNotFound("Status", "name", "Not Available"));
			break;
		}
		return status;
	}

	@Override
	public void deleteById(Integer id) {
		if (!this.existsById(id))
			throw new ResourceNotFound("Status", "id", String.valueOf(id));
		this.statusRepository.deleteById(id);
	}

	@Override
	public StatusDTO update(int id, StatusDTO status) {
		Status oldStatus = this.statusRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Status", "id", String.valueOf(id)));
		Status currentStatus = this.mapperStatus.map(status);
		currentStatus.setId(oldStatus.getId());
		return this.mapperStatus.map(this.statusRepository.save(currentStatus));
	}

}
