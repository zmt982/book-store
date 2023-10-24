package org.bookstore.service;

import org.bookstore.service.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getById(Long id);

    UserDto add(UserDto addDto);

    UserDto updateById(Long id, UserDto updateDto);

    void deleteById(Long id);
}