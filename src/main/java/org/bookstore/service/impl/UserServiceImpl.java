package org.bookstore.service.impl;

import org.bookstore.database.entity.User;
import org.bookstore.database.repository.UserRepository;
import org.bookstore.service.UserService;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAll() {
        Optional<List<User>> optionalUsers = userRepository.findAll();
        List<User> users = optionalUsers.orElseThrow(() -> new RuntimeException("No users found"));
        return userMapper.toDto(users);
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> optionalUserToFind = userRepository.findById(id);
        User userToFind = optionalUserToFind.orElseThrow(() -> new RuntimeException("No user found"));
        return userMapper.toDto(userToFind);
    }

    @Override
    public UserDto add(UserDto addDto) {
        User userToAdd = userMapper.toEntity(addDto);
        Optional<User> optionalUserToAdd = userRepository.save(userToAdd);
        userToAdd = optionalUserToAdd.orElseThrow(() -> new RuntimeException("No user added"));
        return userMapper.toDto(userToAdd);
    }

    @Override
    public UserDto updateById(Long id, UserDto updateDto) {
        User userToUpdate = userMapper.toEntity(updateDto);
        Optional<User> optionalUserToUpdate = userRepository.updateById(id, userToUpdate);
        userToUpdate = optionalUserToUpdate.orElseThrow(() -> new RuntimeException("No user updated"));
        return userMapper.toDto(userToUpdate);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}