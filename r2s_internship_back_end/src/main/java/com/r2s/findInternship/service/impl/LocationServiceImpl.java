package com.r2s.findInternship.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.r2s.findInternship.service.DistrictService;
import com.r2s.findInternship.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.findInternship.dto.LocationDTO;
import com.r2s.findInternship.entity.Location;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperLocation;
import com.r2s.findInternship.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private MapperLocation mapper;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private MessageSource messageSource;
    public final static Logger logger = LoggerFactory.getLogger("info");

    @Override
    public boolean existsById(Integer id) {
        return locationRepository.existsById(id);
    }

    @Override
    public long count() {
        return locationRepository.count();
    }

    @Override
    public void deleteById(Integer id) {
        locationRepository.deleteById(id);
    }

    @Override
    public LocationDTO findById(Integer id) {
        return this.mapper.map(this.getById(id));

    }

    @Override
    public List<LocationDTO> findAll() {
        return locationRepository.findAll(Sort.by(Order.by("address"))).stream().map(location -> this.mapper.map(location)).collect(Collectors.toList());
    }

    //Add location
    @Override
    public LocationDTO save(LocationDTO dto) {
        districtService.findById(dto.getDistrict().getId());
        Location entity = this.mapper.map(dto);
        entity = locationRepository.save(entity);
        logger.info(String.format("Location id %d save successfully", entity.getId()));
        return this.mapper.map(entity);
    }

    @Override
    public LocationDTO update(int id, LocationDTO dto) {
        Location oldLocation = this.getById(id);
        Location newLocation = mapper.map(dto);
        newLocation.setId(id);
        newLocation = locationRepository.save(newLocation);
        logger.info(String.format("Location id %d update successfully", oldLocation.getId()));
        return mapper.map(newLocation);
    }

    @Override
    public Location getById(Integer id) {
        return locationRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Location not found with id: " + id));

    }

    public void delete(Location entity) {
        locationRepository.delete(entity);
        logger.info(String.format("Location id %d  delete successfully", entity.getId()));

    }

    @Override
    public List<LocationDTO> getLocationByDistrictId(Integer id) {
        if (locationRepository.getLocationByDistrictId(id).size() == 0) {
            throw new ResourceNotFound("Location", "id of District", String.valueOf(id));
        }
        return locationRepository.getLocationByDistrictId(id).stream().map(location -> this.mapper.map(location)).collect(Collectors.toList());

    }

    @Override
    public LocationDTO readJson(String value) {
        LocationDTO locationDTO = new LocationDTO();
        try {
            ObjectMapper ob = new ObjectMapper();
            locationDTO = ob.readValue(value, LocationDTO.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
        }
        return locationDTO;
    }

    @Override
    public List<LocationDTO> getLocationByCompanyId(Integer id) {
        if (locationRepository.getLocationByCompanyId(id).size() == 0) {
            throw new ResourceNotFound("Location", "id of Company", String.valueOf(id));
        }
        return locationRepository.getLocationByCompanyId(id).stream().map(location -> this.mapper.map(location)).collect(Collectors.toList());

    }

    @Override
    public Location save(Location entity) {
        logger.info(String.format("Location id %d save successfully", entity.getId()));
        return locationRepository.save(entity);
    }


}
