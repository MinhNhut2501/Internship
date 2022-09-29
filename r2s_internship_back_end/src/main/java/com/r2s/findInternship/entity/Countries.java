package com.r2s.findInternship.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "countries")
public class Countries implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name",length = 50)
	private String name;
	@Column(name = "area_code",length = 50)
	private String areaCode;
	@Column(name = "create_date",columnDefinition = "DATE")
	private LocalDate createDate;
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<Province> provinces = new ArrayList<>();
}
