package onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank
        @Size(max = 50)
        String name,
        @Size(max = 255)
        String description) {
}
