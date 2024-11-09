package edu.gmu.cyse.gta.rest.dto;

import java.time.ZonedDateTime;

public record OrderDto(String id, String description, OrderDto.UserDto user, ZonedDateTime createdAt) {

    public record UserDto(String username) {
    }
}