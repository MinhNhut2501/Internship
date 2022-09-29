package com.r2s.findInternship.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "university_location")
public class University_Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
    @JoinColumn(name = "university_id")
	private University university;
	@ManyToOne
    @JoinColumn(name = "location_id")
	private Location location;
}
