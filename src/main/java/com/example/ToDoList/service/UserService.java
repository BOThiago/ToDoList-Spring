package com.example.ToDoList.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ToDoList.domain.user.User;
import com.example.ToDoList.dto.response.ResponseDTO;
import com.example.ToDoList.dto.response.UserResponseDTO;
import com.example.ToDoList.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            if (userList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            List<UserResponseDTO> userResponseDTOList = userList.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getNivel()))
                .collect(Collectors.toList());

            ResponseDTO<List<UserResponseDTO>> response = new ResponseDTO<>("ok", 200, true, userResponseDTOList);
            return ResponseEntity.ok(response);
        } catch(Exception ex) {
            ResponseDTO<List<UserResponseDTO>> response = new ResponseDTO<>(ex.getMessage(), 500, false, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserById(Long id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getNivel());
                ResponseDTO<UserResponseDTO> response = new ResponseDTO<>("ok", 200, true, userResponseDTO);
                return ResponseEntity.ok(response);
            } else {
                ResponseDTO<UserResponseDTO> response = new ResponseDTO<>("User not found", 404, false, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) { 
            ResponseDTO<UserResponseDTO> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDTO<UserResponseDTO>> addUser(User user) {
        try {
            User newUser = new User(null, user.getUsername(), user.getNivel());
            User savedUser = userRepository.save(newUser);
            UserResponseDTO userResponseDTO = new UserResponseDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getNivel());
            ResponseDTO<UserResponseDTO> response = new ResponseDTO<>("User created", 201, true, userResponseDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO<UserResponseDTO> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDTO<UserResponseDTO>> updateUser(Long id, User user) {
        try {
            Optional<User> userData = userRepository.findById(id);

            if (userData.isPresent()) {
                User updatedUserData = userData.get();
                updatedUserData.setNivel(user.getNivel());
                updatedUserData.setUsername(user.getUsername());

                User userObj = userRepository.save(updatedUserData);
                UserResponseDTO userResponseDTO = new UserResponseDTO(userObj.getId(), userObj.getUsername(), userObj.getNivel());
                ResponseDTO<UserResponseDTO> response = new ResponseDTO<>("User updated", 200, true, userResponseDTO);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            ResponseDTO<UserResponseDTO> response = new ResponseDTO<>("User not found", 404, false, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ResponseDTO<UserResponseDTO> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDTO<Void>> deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            ResponseDTO<Void> response = new ResponseDTO<>("User deleted", 200, true, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
