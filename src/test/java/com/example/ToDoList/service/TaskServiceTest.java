package com.example.ToDoList.service;

import java.util.Arrays;
import java.util.Date;
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
import com.example.ToDoList.domain.status.Status;
import com.example.ToDoList.domain.task.Task;
import com.example.ToDoList.domain.user.User;
import com.example.ToDoList.dto.response.ResponseDTO;
import com.example.ToDoList.dto.response.TaskResponseDTO;
import com.example.ToDoList.repositories.TaskRepository;
import com.example.ToDoList.repositories.UserRepository;

public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_Success() {
        Task task1 = new Task(1L, "Task 1", "Description 1", new Date(), new Date(), Status.PENDENTE, new User(1L, "user1", Nivel.admin));
        Task task2 = new Task(2L, "Task 2", "Description 2", new Date(), new Date(), Status.CONCLUIDA, new User(2L, "user2", Nivel.user));
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> response = taskService.getAllTasks(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().data().size());
    }

    @Test
    void getTaskById_Success() {
        Task task = new Task(1L, "Task 1", "Description 1", new Date(), new Date(), Status.PENDENTE, new User(1L, "user1", Nivel.admin));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<ResponseDTO<TaskResponseDTO>> response = taskService.getTaskById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task 1", response.getBody().data().title());
    }

    @Test
    void addTask_Success() {
        User user = new User(1L, "user1", Nivel.admin);
        Task task = new Task(null, "Task 1", "Description 1", new Date(), new Date(), Status.PENDENTE, user);
        Task savedTask = new Task(1L, "Task 1", "Description 1", new Date(), new Date(), Status.PENDENTE, user);

        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        ResponseEntity<ResponseDTO<TaskResponseDTO>> response = taskService.addTask(task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task 1", response.getBody().data().title());
    }

    @Test
    void updateTask_Success() {
        User user = new User(1L, "user1", Nivel.admin);
        Task existingTask = new Task(1L, "Task 1", "Description 1", new Date(), new Date(), Status.PENDENTE, user);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", new Date(), new Date(), Status.CONCLUIDA, user);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        ResponseEntity<ResponseDTO<TaskResponseDTO>> response = taskService.updateTask(1L, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Task", response.getBody().data().title());
    }

    @Test
    void deleteTask_Success() {
        ResponseEntity<ResponseDTO<Void>> response = taskService.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
