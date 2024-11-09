package edu.gmu.cyse.gta.mapper;

import edu.gmu.cyse.gta.model.Order;
import edu.gmu.cyse.gta.rest.dto.CreateOrderRequest;
import edu.gmu.cyse.gta.rest.dto.OrderDto;

public interface OrderMapper {

    Order toOrder(CreateOrderRequest createOrderRequest);

    OrderDto toOrderDto(Order order);
}