package com.worknest.dao;

import com.worknest.model.Task;
import java.util.List;

public interface TaskDao {
    void save(Task task);
    void update(Task task);
    Task findById(Long taskId);
    List<Task> findAllTasks();
    List<Task> findTasksByStatus(String status);
    List<Task> findTasksByUserId(Long userId);
    void delete(Task task); // New method
}