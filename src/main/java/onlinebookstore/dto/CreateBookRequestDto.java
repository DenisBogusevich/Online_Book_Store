package onlinebookstore.dto;

import java.math.BigDecimal;
import lombok.Data;

public record CreateBookRequestDto(
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage) {
}

