package onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @Size(min = 4, max = 50)
        String email,
        @Size(min = 6, max = 50)
        String password) {
}
