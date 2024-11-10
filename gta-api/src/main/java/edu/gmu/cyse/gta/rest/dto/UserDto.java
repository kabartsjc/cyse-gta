package edu.gmu.cyse.gta.rest.dto;

import java.time.ZonedDateTime;

public record UserDto(Long id, String username, String name, String email, String gmuID, String role) {

	public record OrderDto(String id, String description, ZonedDateTime createdAt) {
	}
}