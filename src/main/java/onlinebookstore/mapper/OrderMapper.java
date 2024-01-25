package onlinebookstore.mapper;

import onlinebookstore.dto.order.OrderDto;
import onlinebookstore.entity.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = OrderItemMapper.class)
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);
}
