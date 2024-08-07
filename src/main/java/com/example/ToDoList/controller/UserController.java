package com.example.ToDoList.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ToDoList.domain.user.User;
import com.example.ToDoList.dto.response.ResponseDTO;
import com.example.ToDoList.dto.response.UserResponseDTO;
import com.example.ToDoList.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
