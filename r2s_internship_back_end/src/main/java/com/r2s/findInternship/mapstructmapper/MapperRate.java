package com.r2s.findInternship.mapstructmapper;

import com.r2s.findInternship.dto.RateDTO;
import com.r2s.findInternship.entity.Rate;

public interface MapperRate {
    Rate map(RateDTO dto);
    RateDTO map(Rate entity);
    Rate mapUpdate(Rate oldEntity, RateDTO dto);
}
