package com.r2s.findInternship.common;

public enum ERole {
	Admin ("Role_Admin"), 
	Cadidate ("Role_Cadidate"), 
	HR ("Role_HR"),
	Partner("Role_Partner");
	private final String name;
	ERole(String string) {
		this.name = string;
	}
	 @Override
     public String toString(){
         return name;
     }
}
