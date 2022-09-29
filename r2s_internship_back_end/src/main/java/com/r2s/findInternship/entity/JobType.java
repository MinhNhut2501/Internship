package com.r2s.findInternship.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "JobType")
public class JobType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name",length = 100)
	private String name;
	@OneToMany(mappedBy = "jobType",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	private List<Job> jobs = new ArrayList<>();
	@OneToMany(mappedBy = "jobType", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<DemandUni> demandUnis = new ArrayList<>();
}
