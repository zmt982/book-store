package org.bookstore.service.mapper.impl;

import org.bookstore.database.entity.Book;
import org.bookstore.database.entity.User;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        final UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());

        if (!CollectionUtils.isEmpty(user.getBooks())) {
            Set<BookDto> books = new HashSet<>();
            for (Book entity : user.getBooks()) {
                BookDto bookDto = new BookDto();
                bookDto.setAuthor(entity.getAuthor());
                bookDto.setName(entity.getName());
                books.add(bookDto);
            }
            userDto.setBooks(books);
        }

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        final User user = new User();
        user.setUsername((userDto.getUsername()));
        return user;
    }

    @Override
    public List<UserDto> toDto(List<User> entities) {
        return entities.stream().map(this::toDto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        return dtoList.stream().map(this::toEntity).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
