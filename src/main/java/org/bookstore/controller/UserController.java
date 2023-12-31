package org.bookstore.controller;

import org.bookstore.service.UserService;
import org.bookstore.service.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-store")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto addDto) {
        return ResponseEntity.ok(userService.add(addDto));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long id, @RequestBody UserDto updateDto) {
        return ResponseEntity.ok(userService.updateById(id, updateDto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(id);
    }

}