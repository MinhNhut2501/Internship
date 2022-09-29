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
@Table(name = "CareList")
public class CareList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JobId")
	private Job jobCare;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CandidateId")
	private Candidate candidateCare;
	@Column(name = "create_date",columnDefinition = "DATE")
	private LocalDate createDate;
	@Column(name = "note",length = 500)
	private String note;
}
