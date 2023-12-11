package onlinebookstore.service;

import onlinebookstore.dto.user.UserRegistrationRequest;
import onlinebookstore.dto.user.UserResponseDto;
import onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;
}
