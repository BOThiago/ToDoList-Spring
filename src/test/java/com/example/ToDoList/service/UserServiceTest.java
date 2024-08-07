package com.example.ToDoList.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ToDoList.domain.nivel.Nivel;
import com.example.ToDoList.domain.user.User;
import com.example.ToDoList.dto.response.ResponseDTO;
import com.example.ToDoList.dto.response.UserResponseDTO;
import com.example.ToDoList.repositories.UserRepository;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_Success() {
        User user1 = new User(1L, "user1", Nivel.admin);
        User user2 = new User(2L, "user2", Nivel.user);
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<ResponseDTO<List<UserResponseDTO>>> response = userService.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().data().size());
    }

    @Test
    void getUserById_Success() {
        User user = new User(1L, "user1", Nivel.admin);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<ResponseDTO<UserResponseDTO>> response = userService.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("user1", response.getBody().data().username());
    }

    @Test
    void addUser_Success() {
        User user = new User(null, "user1", Nivel.admin);
        User savedUser = new User(1L, "user1", Nivel.admin);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        ResponseEntity<ResponseDTO<UserResponseDTO>> response = userService.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("user1", response.getBody().data().username());
    }

    @Test
    void updateUser_Success() {
        User existingUser = new User(1L, "user1", Nivel.admin);
        User updatedUser = new User(1L, "updatedUser", Nivel.admin);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        ResponseEntity<ResponseDTO<UserResponseDTO>> response = userService.updateUser(1L, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("updatedUser", response.getBody().data().username());
    }

    @Test
    void deleteUser_Success() {
        ResponseEntity<ResponseDTO<Void>> response = userService.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
