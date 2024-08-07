package com.example.ToDoList.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ToDoList.domain.status.Status;
import com.example.ToDoList.domain.task.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findByStatusOrderByDueDate(Status status);
    List<Task> findByUserId(Long userId);
}