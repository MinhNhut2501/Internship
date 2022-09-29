package com.r2s.findInternship.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RateDTO {
    private int id;
    private UserDTO user;
    private CompanyDTO company;
    private int score;
    private String title;
    private String comment;
    private LocalDate createDate;
    private StatusDTO status;
    private boolean hide;
}
