package com.r2s.findInternship.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "HR")
public class HR {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserId")
	private User user;
	@Column(length = 100)
	private String position;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CompanyId")
	private Company company;
}
