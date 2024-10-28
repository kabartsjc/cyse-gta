package edu.gmu.cyse.gta.enums;

public enum UserRole {
	ADMIN("admin"),
	GTA("gta"),
	FACULTY("faculty"),
	CHAIR("chair");
	
	private String role;
	
	UserRole(String role){
		this.role=role;
	}
	
	public String getValue() {
		return role;
	}
	
}
