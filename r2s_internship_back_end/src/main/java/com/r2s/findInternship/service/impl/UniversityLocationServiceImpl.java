package com.r2s.findInternship.service.impl;

import com.r2s.findInternship.dto.UniversityLocationDTO;
import com.r2s.findInternship.exception.ResourceNotFound;
import com.r2s.findInternship.mapstructmapper.MapperUniversityLocation;
import com.r2s.findInternship.repository.UniversityLocationRepository;
import com.r2s.findInternship.service.UniversityLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UniversityLocationServiceImpl implements UniversityLocationService {
    @Autowired
    private MapperUniversityLocation mapperUniversityLocation;
    @Autowired
    private UniversityLocationRepository universityLocationRepository;

    @Override
    public UniversityLocationDTO findById(int id) {
        return mapperUniversityLocation
                .map(universityLocationRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFound(
                                "UniversityLocation", "id", String.valueOf(id)
                        )));
    }
}
