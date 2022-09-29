package com.r2s.findInternship.entity;



import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ApplyList")
public class ApplyList implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JobId")
	private Job jobApp;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CandidateId")
	private Candidate candidate;
	@Column(name = "create_date",columnDefinition = "DATE")
	private LocalDate createDate;
	@Column(name = "reference_letter",length = 500)
	private String referenceLetter;
	@Column(name = "CV", length = 250)
	private String CV;
}
