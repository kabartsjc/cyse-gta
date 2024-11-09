package edu.gmu.cyse.gta.rest.dto;

import java.time.ZonedDateTime;

public record ApplicationDto(String id, String description, ApplicationDto.UserDto user, ZonedDateTime createdAt) {

    public record UserDto(String username) {
    }
}