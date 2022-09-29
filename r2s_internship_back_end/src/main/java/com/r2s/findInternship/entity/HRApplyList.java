package com.r2s.findInternship.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "HRApplyList")
public class HRApplyList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DemandId")
	private DemandUni demandUni;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HrId")
	private HR hr;
	@OneToOne
	@JoinColumn(name = "statusId")
	private Status status;
	@Column(name = "date",columnDefinition = "DATE")
	private LocalDate date;
	@Column(name = "note",length = 500)
	private String note;
}
