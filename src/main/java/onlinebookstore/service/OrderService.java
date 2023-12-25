package onlinebookstore.service;

import java.util.Set;
import onlinebookstore.dto.order.CreateOrderRequestDto;
import onlinebookstore.dto.order.OrderDto;
import onlinebookstore.dto.order.OrderItemDto;
import onlinebookstore.dto.order.UpdateOrderRequestDto;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequestDto requestDto);

    Set<OrderDto> getOrders();

    OrderDto updateOrder(Long orderId, UpdateOrderRequestDto requestDto);

    Set<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    OrderItemDto getOrderItemFromOrder(Long orderId, Long itemId);
}
