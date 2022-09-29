package com.r2s.findInternship.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "university_location")
public class UniversityLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "university_id")
	private University university;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
	private Location location;
}
