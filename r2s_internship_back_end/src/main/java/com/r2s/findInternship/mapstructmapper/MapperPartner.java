package com.r2s.findInternship.mapstructmapper;

import org.mapstruct.Mapper;

import com.r2s.findInternship.dto.PartnerCreateDTO;
import com.r2s.findInternship.dto.PartnerDTO;
import com.r2s.findInternship.dto.show.PartnerUniversityShow;
import com.r2s.findInternship.entity.Partner;

@Mapper(componentModel = "spring")
public interface MapperPartner {
	Partner map(PartnerDTO dto);
	PartnerDTO map(Partner dto);
	Partner map(PartnerCreateDTO dto);
	PartnerUniversityShow mapToUniversityShow(Partner entity);
}
