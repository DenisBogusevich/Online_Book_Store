package onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest (
    @NotBlank
    @Email
    @Size(min =4, max = 50)
    String email,

    @NotBlank
    @Size(min =6, max = 50)
    String password ) {
}
