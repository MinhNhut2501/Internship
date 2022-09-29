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
@Table(name = "Major")
public class Major {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",columnDefinition = "INT(10)")
	private int id;
	@Column(name = "name", length = 200, nullable = false)
	private String name;
	@Column(name = "create_date",columnDefinition = "DATE")
	private LocalDate createDate;
	@OneToMany(mappedBy = "major",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	private List<Job> jobs = new ArrayList<>();
	@OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<DemandUni> demandUnis = new ArrayList<>();
}
