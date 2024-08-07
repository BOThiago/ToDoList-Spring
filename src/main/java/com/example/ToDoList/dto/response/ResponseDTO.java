package com.example.ToDoList.dto.response;

public record ResponseDTO<T>(String message, Integer statusCode, Boolean success, T data) { }
