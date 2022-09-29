package com.r2s.findInternship.dto;

import java.time.LocalDate;

import com.r2s.findInternship.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HRApplyListDTO {
	private int id;
	private DemandUniDTO demandUni;
	private HRDTO hr;
	private LocalDate date;
	private Status status;
	private String note;
}
