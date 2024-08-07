package com.example.ToDoList.dto.response;

import java.util.Date;

import com.example.ToDoList.domain.status.Status;
import com.example.ToDoList.domain.user.User;

public record TaskResponseDTO (Long id, String title, String description, Date createdAt, Date dueDate, Status status, User user) { }