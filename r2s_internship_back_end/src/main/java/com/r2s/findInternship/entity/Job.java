package com.r2s.findInternship.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "job")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HrId")
	private HR hr;
	@Column(length = 5000)
	private String desciption;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MajorId")
	private Major major;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PositionId")
	private JobPosition jobposition;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TypeId")
	private JobType jobType;
	private int amount;
	@Column(name = "salary_min",columnDefinition = "bigint")
	private long salaryMin;
	@Column(name = "salary_max",columnDefinition = "bigint")
	private long salaryMax;
	@Column(name = "requirement",length = 1000,nullable = true)
	private String requirement;
	@Column(name = "other_info",length = 1000)
	private String otherInfo;
	@Column(name = "time_start",columnDefinition = "DATE")
	private LocalDate timeStart;
	@Column(name = "time_end",columnDefinition = "DATE")
	private LocalDate timeEnd;//	localDate
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location locationjob;
	@Column(name = "create_Date",columnDefinition = "DATE")
	private LocalDate createDate;
	@OneToOne
	@JoinColumn(name = "status_id")
	private Status status;
	//How many times Candidate click Apply
	@OneToMany(mappedBy = "jobApp",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	private List<ApplyList> apply = new ArrayList<>();
}
