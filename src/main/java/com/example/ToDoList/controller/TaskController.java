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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Task Management System", description = "Operations pertaining to tasks in the Task Management System")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Operation(summary = "View a list of all tasks")
    @GetMapping("/tasks")
    public ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> getAllTasks(
        @Parameter(description = "Status of the task")
        @RequestParam(value = "status", required = false) Status status,
        @Parameter(description = "Sort tasks by due date")
        @RequestParam(value = "sort", required = false) String sort) {
        return taskService.getAllTasks(status, sort);
    }

    @Operation(summary = "Get a task by Id")
    @GetMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> getTaskById(
        @Parameter(description = "Task id from which task object will retrieve", required = true)
        @PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Add a new task")
    @PostMapping("/tasks")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> addTask(
        @Parameter(description = "Task object to be stored in database", required = true)
        @RequestBody Task task) {
        return taskService.addTask(task);
    }

    @Operation(summary = "Update an existing task")
    @PutMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> updateTask(
        @Parameter(description = "Task Id to update task object", required = true)
        @PathVariable Long id,
        @Parameter(description = "Updated task object", required = true)
        @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @Operation(summary = "Delete a task by Id")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteTask(
        @Parameter(description = "Task Id from which task object will delete from database", required = true)
        @PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}
