package org.bookstore.service.impl;

import org.bookstore.database.entity.User;
import org.bookstore.database.repository.UserRepository;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;
    private UserDto userDto;
    private User user;

    @BeforeEach
    void init() {
        userDto = new UserDto();
        userDto.setUsername("John");

        user = new User();
        user.setId(1L);
        user.setUsername("John");
    }

    @Test
    void getAll() {
        List<User> users = Collections.singletonList(user);
        List<UserDto> expected = Collections.singletonList(userDto);

        when(userRepository.findAll()).thenReturn(Optional.ofNullable(users));
        when(userMapper.toDto(users)).thenReturn(expected);

        List<UserDto> result = userService.getAll();

        assertEquals(expected, result);

        verify(userRepository).findAll();
        verify(userMapper).toDto(users);
    }

    @Test
    void getById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getById(1L);

        assertEquals(userDto, result);

        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    void add() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(Optional.ofNullable(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.add(userDto);

        assertEquals(userDto, result);

        verify(userMapper).toEntity(userDto);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void updateById() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.updateById(1L, user)).thenReturn(Optional.ofNullable(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.updateById(1L, userDto);

        assertEquals(userDto, result);

        verify(userMapper).toEntity(userDto);
        verify(userRepository).updateById(1L, user);
        verify(userMapper).toDto(user);
    }

    @Test
    void deleteById() {
        userService.deleteById(1L);

        verify(userRepository).deleteById(1L);
    }
}