package com.r2s.findInternship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityLocationDTO {
    private int id;
    private UniversityDTO university;
    private LocationDTO location;
}
