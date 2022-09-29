package com.r2s.findInternship.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "username",unique = true, nullable = false,length = 200)
	private String username;
	@Column(name = "password",nullable = false, length = 200)
	private String password;
	@Column(name = "gender",columnDefinition = "TINYINT")
	private int gender;
	@Column(name = "avatar",length = 200)
	private String avatar;
	@Column(name = "phone",length = 50)
	private String phone;
	private String firstName;
	private String lastName;
	@Column(name = "email",length = 200)
	private String email;
	@Column(name = "create_Date",columnDefinition = "DATE")
	private LocalDate createDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_id")
	private Status status;
	@OneToOne
	@JoinColumn(name = "Role_Id",nullable = true)
	private Role role;
}
