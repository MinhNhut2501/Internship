package com.r2s.findInternship.dto;

import lombok.Data;

@Data
public class JwtRespone {
	private String token;
	private String type="Bearer";

	private String username;
	private String email;
	private String role;
	private String avatar;
	private long idUser;
	public JwtRespone(String accessToken,  String username, String email, String roles, String avatar, long id)
	{	this.idUser = id;
		this.token = accessToken;
		this.avatar = avatar;
		this.username = username;
		this.email = email;
		this.role = roles;
	}
}
