package onlinebookstore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String isbn,
        @NotBlank
        @PositiveOrZero
        BigDecimal price,
        String description,
        String coverImage,
        @NotBlank
        List<Long> categoryIds) {
}
