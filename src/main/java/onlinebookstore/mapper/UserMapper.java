package onlinebookstore.mapper;

import onlinebookstore.dto.user.UserRegistrationRequest;
import onlinebookstore.dto.user.UserResponseDto;
import onlinebookstore.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface UserMapper {
    UserResponseDto toUserDto(User user);

    User toUser(UserRegistrationRequest userDto);
}
