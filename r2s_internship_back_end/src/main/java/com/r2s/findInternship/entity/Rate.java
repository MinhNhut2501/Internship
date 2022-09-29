package com.r2s.findInternship.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Rate")
public class Rate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CompanyId")
	private Company company;
	@Column(name = "score",columnDefinition = "INT(6)")
	private int score;
	@Column(name = "title", length = 20)
	private String title;
	@Column(name = "comment")
	private String comment;
	@Column(name = "create_Date",columnDefinition = "DATE")
	private LocalDate createDate;
	@OneToOne
	@JoinColumn(name = "status_id")
	private Status status;
	private boolean hide;
}
