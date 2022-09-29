package com.r2s.findInternship.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Role")
public class Role {
	public static final String candidate = "Role_Candidate";
	public static final String admin = "Role_Admin";
	public static final String hr = "Role_HR";
	public static final String partner = "Role_Partner";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",columnDefinition = "INT(10)")
	private int id;
	@Column(name = "name",nullable = false, length = 50,unique = true)
	private String name;
}
