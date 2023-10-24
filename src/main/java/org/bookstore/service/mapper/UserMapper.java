package org.bookstore.service.mapper;

import org.bookstore.database.entity.User;
import org.bookstore.service.model.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDto(List<User> entities);

    List<User> toEntity(List<UserDto> dtoList);
}
