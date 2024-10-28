package edu.gmu.cyse.gta.dtos;

import edu.gmu.cyse.gta.enums.UserRole;

public record SignUpDto( String login,
	    String password,
	    UserRole role) { 
}
