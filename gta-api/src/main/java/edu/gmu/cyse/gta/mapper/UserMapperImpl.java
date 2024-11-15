package edu.gmu.cyse.gta.mapper;

import edu.gmu.cyse.gta.model.Order;
import edu.gmu.cyse.gta.model.User;
import edu.gmu.cyse.gta.rest.dto.UserDto;
import org.springframework.stereotype.Service;


@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(),user.getGmuID(),user.getRole());
    }

    @SuppressWarnings("unused")
	private UserDto.OrderDto toUserDtoOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        return new UserDto.OrderDto(order.getId(), order.getDescription(), order.getCreatedAt());
    }
}
