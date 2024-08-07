package com.example.ToDoList.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ToDoList.domain.status.Status;
import com.example.ToDoList.domain.task.Task;
import com.example.ToDoList.dto.response.ResponseDTO;
import com.example.ToDoList.dto.response.TaskResponseDTO;
import com.example.ToDoList.repositories.TaskRepository;
import com.example.ToDoList.repositories.UserRepository;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> getAllTasks(Status status, String sort) {
        try {
            List<Task> tasks;
            if (status != null && sort != null && sort.equals("dueDate")) {
                tasks = taskRepository.findByStatusOrderByDueDate(status);
            } else if (status != null) {
                tasks = taskRepository.findByStatus(status);
            } else if (sort != null && sort.equals("dueDate")) {
                tasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "dueDate"));
            } else {
                tasks = taskRepository.findAll();
            }
            
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<TaskResponseDTO> taskResponseDTOs = tasks.stream()
                .map(task -> new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedAt(), task.getDueDate(), task.getStatus(), task.getUser()))
                .collect(Collectors.toList());

            ResponseDTO<List<TaskResponseDTO>> response = new ResponseDTO<>("ok", 200, true, taskResponseDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO<List<TaskResponseDTO>> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDTO<TaskResponseDTO>> getTaskById(Long id) {
        Optional<Task> taskObj = taskRepository.findById(id);
        if (taskObj.isPresent()) {
            Task task = taskObj.get();
            TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedAt(), task.getDueDate(), task.getStatus(), task.getUser());
            ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>("ok", 200, true, taskResponseDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>("Task not found", 404, false, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseDTO<TaskResponseDTO>> addTask(Task task) {
        try {
            if (task.getUser() == null || !userRepository.existsById(task.getUser().getId())) {
                ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>("User not found", 404, false, null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Task taskObj = taskRepository.save(task);
            TaskResponseDTO taskResponseDTO = new TaskResponseDTO(taskObj.getId(), taskObj.getTitle(), taskObj.getDescription(), taskObj.getCreatedAt(), taskObj.getDueDate(), taskObj.getStatus(), taskObj.getUser());
            ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>("Task created", 201, true, taskResponseDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDTO<TaskResponseDTO>> updateTask(Long id, Task task) {
        try {
            Optional<Task> taskData = taskRepository.findById(id);

            if (task.getUser() == null || !userRepository.existsById(task.getUser().getId())) {
                ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>("User not found", 404, false, null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            if (taskData.isPresent()) {
                Task updatedTaskData = taskData.get();
                updatedTaskData.setTitle(task.getTitle());
                updatedTaskData.setDescription(task.getDescription());
                updatedTaskData.setDueDate(task.getDueDate());
                updatedTaskData.setStatus(task.getStatus());
    
                Task taskObj = taskRepository.save(updatedTaskData);
                TaskResponseDTO taskResponseDTO = new TaskResponseDTO(taskObj.getId(), taskObj.getTitle(), taskObj.getDescription(), taskObj.getCreatedAt(), taskObj.getDueDate(), taskObj.getStatus(), taskObj.getUser());
                ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>("Task updated", 200, true, taskResponseDTO);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
    
            ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>("Task not found", 404, false, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ResponseDTO<TaskResponseDTO> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    public ResponseEntity<ResponseDTO<Void>> deleteTask(Long id) {
        try {
            taskRepository.deleteById(id);
            ResponseDTO<Void> response = new ResponseDTO<>("Task deleted", 204, true, null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> getTasksByUserId(Long userId) {
        try {
            if (!userRepository.existsById(userId)) {
                ResponseDTO<List<TaskResponseDTO>> response = new ResponseDTO<>("User not found", 404, false, null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            List<Task> tasks = taskRepository.findByUserId(userId);
            if (tasks.isEmpty()) {
                ResponseDTO<List<TaskResponseDTO>> response = new ResponseDTO<>("No tasks found", 204, true, null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }

            List<TaskResponseDTO> taskResponseDTOs = tasks.stream()
                .map(task -> new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedAt(), task.getDueDate(), task.getStatus(), task.getUser()))
                .collect(Collectors.toList());

            ResponseDTO<List<TaskResponseDTO>> response = new ResponseDTO<>("ok", 200, true, taskResponseDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO<List<TaskResponseDTO>> response = new ResponseDTO<>(e.getMessage(), 500, false, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
