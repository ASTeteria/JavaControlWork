package javapring.javacw.mapper;

import javapring.javacw.dto.UserDto;
import javapring.javacw.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true) // Пароль не передається у DTO
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
