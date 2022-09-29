package com.r2s.findInternship.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "University")
public class University {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name",nullable = false)
	private String name;
	@Column(name = "avatar",nullable = false, length = 100)
	private String avatar;
	@Column(name = "ShortName",nullable = false, length = 50)
	private String shortName;
	@Column(name = "description", length = 5000)
	private String description;
	@Column(name = "website", length = 200)
	private String website;
	@Column(name = "email",nullable = false, length = 200)
	private String email;
	@Column(name = "phone",length = 50)
	private String phone;
	private LocalDate createDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_id")
	private Status status;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "TypeUniversity")
	private TypeUniversity typeUniversity;
	@OneToMany(mappedBy = "university",fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<UniversityLocation> universityLocations = new ArrayList<>();
}
