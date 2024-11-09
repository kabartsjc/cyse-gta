package edu.gmu.cyse.gta.mapper;

import edu.gmu.cyse.gta.model.User;
import edu.gmu.cyse.gta.rest.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}