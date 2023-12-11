package onlinebookstore.service;

import onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto findById(Long id);

    ShoppingCartDto addToCart(Long id, CreateCartItemRequestDto requestDto);

    ShoppingCartDto update(Long userId, Long cartItemId, UpdateCartItemRequestDto quantityDto);

    ShoppingCartDto removeCartItem(Long userId, Long cartItemId);
}
