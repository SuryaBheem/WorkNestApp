package com.example.service;

import com.example.entity.Task;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.repository.TaskAssignmentRepository;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final TaskAssignmentRepository assignRepo;

    public TaskService(TaskRepository taskRepo, TaskAssignmentRepository assignRepo) {
        this.taskRepo = taskRepo;
        this.assignRepo = assignRepo;
    }

    // Create or update task
    public Task create(Task t) {
        return taskRepo.save(t);
    }

    // Find task by id
    public Optional<Task> findById(Long id) {
        return taskRepo.findById(id);
    }

    // Find all tasks
    public List<Task> findAll() {
        return taskRepo.findAll();
    }

    // Find tasks by status
    public List<Task> findByStatus(Task.Status status) {
        return taskRepo.findByStatus(status);
    }

    // ✅ Assign task to user
    public TaskAssignment assignTaskToUser(Task task, User user, String assignedBy) {
        TaskAssignment ta = new TaskAssignment();
        ta.setTask(task);
        ta.setUser(user);
        ta.setAssignedBy(assignedBy);
        return assignRepo.save(ta);
    }

    // Get assignments for a task
    public List<TaskAssignment> getAssignmentsForTask(Task task) {
        return assignRepo.findByTask(task);
    }

    // Get assignments for a user
    public List<TaskAssignment> getAssignmentsForUser(User user) {
        return assignRepo.findByUser(user);
    }

    // Get assignments for a specific user and task
    public List<TaskAssignment> getAssignmentsForUserAndTask(User user, Task task) {
        return assignRepo.findByUserAndTask(user, task);
    }

    // Delete an assignment
    public void deleteAssignment(Long id) {
        assignRepo.deleteById(id);
    }
}
