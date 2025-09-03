package com.example.repository;

import com.example.entity.Task;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUsersContaining(User user);
    List<Task> findByStatus(Task.Status status);
}
