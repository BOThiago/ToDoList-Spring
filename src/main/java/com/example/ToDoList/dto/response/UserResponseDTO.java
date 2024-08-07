package com.example.ToDoList.dto.response;

import com.example.ToDoList.domain.nivel.Nivel;

public record UserResponseDTO (Long id, String username, Nivel nivel) {}