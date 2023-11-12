package onlinebookstore.dto.book;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private String coverImage;
    private List<Long> categoryIds;
}
