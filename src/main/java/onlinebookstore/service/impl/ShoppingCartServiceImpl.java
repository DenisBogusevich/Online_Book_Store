package onlinebookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import onlinebookstore.entity.Book;
import onlinebookstore.entity.CartItem;
import onlinebookstore.entity.ShoppingCart;
import onlinebookstore.entity.User;
import onlinebookstore.mapper.ShoppingCartMapper;
import onlinebookstore.repository.BookRepository;
import onlinebookstore.repository.CartItemRepository;
import onlinebookstore.repository.ShoppingCartRepository;
import onlinebookstore.repository.UserRepository;
import onlinebookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    @Override
    public ShoppingCartDto findById(Long id) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find shopping cart")
        ));
    }

    @Transactional
    @Override
    public ShoppingCartDto addToCart(Long userId, CreateCartItemRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find user")
        );
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseGet(
                () -> createShoppingCartForUser(user)
        );
        Book book = bookRepository.findById(requestDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find book")
        );
        CartItem cartItem = getCartItem(requestDto, shoppingCart, book);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    private static CartItem getCartItem(CreateCartItemRequestDto requestDto,
                                        ShoppingCart shoppingCart, Book book) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(requestDto.quantity());
        shoppingCart.getCartItems().add(cartItem);
        return cartItem;
    }

    @Transactional
    @Override
    public ShoppingCartDto update(Long userId,
                                  Long cartItemId,
                                  UpdateCartItemRequestDto quantityDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by id " + userId)
                );
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by id " + cartItemId + "for user by id " + userId)
                );
        cartItem.setQuantity(quantityDto.quantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(cartItem.getShoppingCart());
    }

    @Transactional
    @Override
    public ShoppingCartDto removeCartItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by id " + userId)
                );
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by id " + cartItemId + "for user by id " + userId)
                );
        cartItemRepository.delete(cartItem);
        shoppingCart.removeCartItem(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    private ShoppingCart createShoppingCartForUser(User user) {
        ShoppingCart newShoppingCart = new ShoppingCart();
        newShoppingCart.setUser(user);
        return shoppingCartRepository.save(newShoppingCart);
    }
}
