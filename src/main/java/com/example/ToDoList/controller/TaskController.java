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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ToDoList.domain.status.Status;
import com.example.ToDoList.domain.task.Task;
import com.example.ToDoList.dto.response.ResponseDTO;
import com.example.ToDoList.dto.response.TaskResponseDTO;
import com.example.ToDoList.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> getAllTasks(
        @RequestParam(value = "status", required = false) Status status,
        @RequestParam(value = "sort", required = false) String sort) {
        return taskService.getAllTasks(status, sort);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/tasks")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }    

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}
