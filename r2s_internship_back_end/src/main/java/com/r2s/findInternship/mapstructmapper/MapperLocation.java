package com.r2s.findInternship.mapstructmapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.LocationDTO;
import com.r2s.findInternship.entity.Location;

@Mapper(componentModel = "spring")
public interface MapperLocation {
	Location map(LocationDTO dto);
	LocationDTO map(Location entity);
}
