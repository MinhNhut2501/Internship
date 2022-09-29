package com.r2s.findInternship.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Location")
public class Location implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String address;
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private District district;

	@OneToMany(mappedBy = "locationjob", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<Job> jobs = new ArrayList<>();

	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<UniversityLocation> universityLocations = new ArrayList<>();

	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<CompanyLocation> companyLocations = new ArrayList<>();

//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable( name =  "company_location" ,
//		joinColumns = {@JoinColumn(name="location_id")},
//		inverseJoinColumns = {@JoinColumn(name="company_id")})
//	private Set<Company> companies = new HashSet<Company>();
//	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "university_location",joinColumns = { @JoinColumn(name="location_id")},
//			inverseJoinColumns = {@JoinColumn(name = "university_id")})
//	private Set<University> universities = new HashSet<University>();

	@Override
	public String toString() {
		return this.address + ", " + this.district.getName() + ", " + this.getDistrict().getProvince().getName();
	}
	
	public String show() {
		return note +", " + address+", " + district.getName()+", " + district.getProvince().getName()+", "+district.getProvince().getCountry().getName();
	}

}
