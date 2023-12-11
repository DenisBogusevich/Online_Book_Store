package onlinebookstore.mapper;

import onlinebookstore.dto.shoppingcart.CartItemDto;
import onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import onlinebookstore.entity.CartItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = BookMapper.class
)
public interface CartItemMapper {
    CartItem toCartItem(CreateCartItemRequestDto requestDto);

    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.id", target = "bookId")
    CartItemDto toDto(CartItem cartItem);
}
