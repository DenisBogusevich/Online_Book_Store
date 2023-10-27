package onlinebookstore.dto;

import java.math.BigDecimal;
import lombok.Data;

public record BookDto(
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage) {
}

