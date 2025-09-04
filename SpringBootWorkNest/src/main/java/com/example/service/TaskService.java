package com.example.service;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.repository.CommentRepository;
import com.example.repository.TaskAssignmentRepository;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final TaskAssignmentRepository assignRepo;
    private final CommentRepository commentRepo;

    public TaskService(TaskRepository taskRepo, TaskAssignmentRepository assignRepo, CommentRepository commentRepo) {
        this.taskRepo = taskRepo;
        this.assignRepo = assignRepo;
        this.commentRepo = commentRepo;
    }

    public Task create(Task t) { return taskRepo.save(t); }
    public Optional<Task> findById(Long id) { return taskRepo.findById(id); }
    public List<Task> findAll() { return taskRepo.findAll(); }
    public List<Task> findByStatus(Task.Status status) { return taskRepo.findByStatus(status); }

    public TaskAssignment assignTaskToUser(Task task, User user, String assignedBy) {
        TaskAssignment ta = new TaskAssignment();
        ta.setTask(task);
        ta.setUser(user);
        ta.setAssignedBy(assignedBy);
        return assignRepo.save(ta);
    }

    public List<TaskAssignment> getAssignmentsForTask(Task task) { return assignRepo.findByTask(task); }
    public List<TaskAssignment> getAssignmentsForUser(User user) { return assignRepo.findByUser(user); }
    public List<TaskAssignment> getAssignmentsForUserAndTask(User user, Task task) { return assignRepo.findByUserAndTask(user, task); }
    public void deleteAssignment(Long id) { assignRepo.deleteById(id); }

    // Delayed tasks
    public List<Task> findDelayedTasks() {
        LocalDate today = LocalDate.now();
        return taskRepo.findAll().stream()
                .filter(t -> t.getDueDate() != null 
                        && t.getDueDate().isBefore(today) 
                        && t.getStatus() != Task.Status.COMPLETED)
                .toList();
    }

    // ------------------- COMMENTS SUPPORT -------------------
    public Comment addComment(Task task, User user, String text) {
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUser(user);
        comment.setText(text);
        return commentRepo.save(comment);
    }

    public List<Comment> getCommentsForTask(Task task) {
        return commentRepo.findByTask(task);
    }
}
