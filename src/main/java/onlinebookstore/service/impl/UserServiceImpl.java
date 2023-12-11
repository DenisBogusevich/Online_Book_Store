package onlinebookstore.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import onlinebookstore.dto.user.UserRegistrationRequest;
import onlinebookstore.dto.user.UserResponseDto;
import onlinebookstore.entity.Role;
import onlinebookstore.entity.User;
import onlinebookstore.exception.RegistrationException;
import onlinebookstore.mapper.UserMapper;
import onlinebookstore.repository.RoleRepository;
import onlinebookstore.repository.UserRepository;
import onlinebookstore.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequest request) throws RegistrationException {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RegistrationException("Email already exists");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(getUserRole());
        return userMapper.toUserDto(userRepository.save(user));
    }

    private Set<Role> getUserRole() {
        return new HashSet<>(Collections.singletonList(
                roleRepository.findRoleByName(Role.RoleName.ROLE_USER)
        ));
    }
}
