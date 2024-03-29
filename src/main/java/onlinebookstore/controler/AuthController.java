package onlinebookstore.controler;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import onlinebookstore.dto.user.UserLoginRequest;
import onlinebookstore.dto.user.UserLoginResponse;
import onlinebookstore.dto.user.UserRegistrationRequest;
import onlinebookstore.dto.user.UserResponseDto;
import onlinebookstore.exception.RegistrationException;
import onlinebookstore.security.AuthService;
import onlinebookstore.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequest userRegistrationRequest)
             throws RegistrationException {
        return userService.register(userRegistrationRequest);
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return authService.authenticate(userLoginRequest);
    }
}
