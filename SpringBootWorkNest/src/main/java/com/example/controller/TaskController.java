package com.example.controller;

import com.example.entity.Task;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import com.example.entity.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskController(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    // Admin view of tasks
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "admin/tasks"; // admin/tasks.html
    }

    // Create + assign task
    @PostMapping
    public String saveTask(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam List<Long> userIds) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(Task.Status.PENDING);
        task.setAssignedUsers(userRepo.findAllById(userIds));
        taskRepo.save(task);
        return "redirect:/tasks";
    }

    // Delete task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepo.deleteById(id);
        return "redirect:/tasks";
    }
}
