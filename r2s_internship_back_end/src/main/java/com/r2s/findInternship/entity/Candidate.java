package com.r2s.findInternship.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Candidate")
public class Candidate implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "CV",length = 1000)
	private String CV;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User user;
	@OneToOne
	@JoinColumn(name = "MajorId")
	private Major major;
	@OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL)
	private List<ApplyList> applyLists = new ArrayList<>();
	@OneToMany(mappedBy = "candidateCare",cascade = CascadeType.ALL)
	private List<CareList> careJobs = new ArrayList<>();

}
