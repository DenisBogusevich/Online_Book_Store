package onlinebookstore.dto.shoppingcart;

import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(
        @Positive
        Long bookId,
        @Positive
        int quantity) {
}
