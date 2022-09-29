package com.r2s.findInternship.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "company")
public class Company   {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name", length = 200	,nullable = true)
	private String name;
	@Column(name = "logo", length = 150)
	private String logo;
	@Column(name = "description",length = 5000)
	private String description;
	@Column(name = "website",length = 200)
	private String website;
	@Column(name = "email",length = 200)
	private String email;
	@Column(name = "phone",length = 50)
	private String phone;
	@Column(name = "tax",length = 50)
	private String tax;
	@Column(name = "date",columnDefinition = "DATE")
	private LocalDate date;
	@OneToOne
	@JoinColumn(name = "status_id")
	private Status status;
	@OneToMany(mappedBy = "company",fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<Rate> rates = new ArrayList<>();
	@OneToMany(mappedBy = "company",fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<CompanyLocation> companyLocations = new ArrayList<>();
}
