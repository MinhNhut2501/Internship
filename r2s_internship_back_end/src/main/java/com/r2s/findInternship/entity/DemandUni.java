package com.r2s.findInternship.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DemandUni")
public class DemandUni {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	//1part co nhieu demand
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PartnerId")
	private Partner partner;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MajorId")
	private Major major;
	@Column(length = 5000)
	private String desciption;
	private long amount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PositionId")
	private JobPosition position;
	@Column(name = "requirement",length = 1000,nullable = true)
	private String requirement;
	@Column(name = "other_info",length = 1000)
	private String ortherInfo;
	@Column(name = "start",columnDefinition = "DATE")
	private LocalDate start;
	@Column(name = "end",columnDefinition = "DATE")
	private LocalDate end;
	@Column(length = 1000)
	private String students;
	@Column(name = "create_date",columnDefinition = "DATE")
	private LocalDate createDate;
	private boolean status;
	@Column(name = "updateDate",columnDefinition = "DATE")
	private LocalDate updateDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeId")
	private JobType jobType;
}
