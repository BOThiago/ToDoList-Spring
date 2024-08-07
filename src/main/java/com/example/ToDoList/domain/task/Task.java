package com.example.ToDoList.domain.task;

import java.util.Date;

import com.example.ToDoList.domain.status.Status;
import com.example.ToDoList.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tasks")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
