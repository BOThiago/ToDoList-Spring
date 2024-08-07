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
import com.example.ToDoList.dto.response.TaskResponseDTO;
import com.example.ToDoList.dto.response.UserResponseDTO;
import com.example.ToDoList.service.TaskService;
import com.example.ToDoList.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "User Management System", description = "Operations pertaining to users in the User Management System")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Operation(summary = "View a list of all users")
    @GetMapping("/users")
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get a user by Id")
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserById(
            @Parameter(description = "User id from which user object will retrieve", required = true)
            @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Add a new user")
    @PostMapping("/users")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> addUser(
            @Parameter(description = "User object to be stored in database", required = true)
            @RequestBody User user) {
        return userService.addUser(user);
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> updateUser(
            @Parameter(description = "User Id to update user object", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated user object", required = true)
            @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @Operation(summary = "Delete a user by Id")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(
            @Parameter(description = "User Id from which user object will delete from database", required = true)
            @PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Get tasks by user Id")
    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> getTasksByUserId(
            @Parameter(description = "User Id from which tasks will retrieve", required = true)
            @PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }
}
