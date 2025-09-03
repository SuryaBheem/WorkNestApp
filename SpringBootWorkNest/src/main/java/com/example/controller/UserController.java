package com.example.controller;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.repository.CommentRepository;
import com.example.repository.TaskAssignmentRepository;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final TaskAssignmentRepository assignRepo;
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final CommentRepository commentRepo;

    public UserController(TaskAssignmentRepository assignRepo,
                          TaskRepository taskRepo,
                          UserRepository userRepo,
                          CommentRepository commentRepo) {
        this.assignRepo = assignRepo;
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
    }

    // User dashboard -> assigned tasks
    @GetMapping("/tasks")
    public String myTasks(Authentication auth, Model model) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        model.addAttribute("assignments", assignRepo.findByUser(user));
        return "user/tasks"; // user/tasks.html
    }

    // Update status
    @PostMapping("/task/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        Task task = taskRepo.findById(id).orElseThrow();
        task.setStatus(Task.Status.valueOf(status));
        taskRepo.save(task);
        return "redirect:/user/tasks";
    }

    // Add comment
    @PostMapping("/task/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String text,
                             Authentication auth) {
        Task task = taskRepo.findById(id).orElseThrow();
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        Comment c = new Comment();
        c.setTask(task);
        c.setUser(user);
        c.setText(text);
        commentRepo.save(c);
        return "redirect:/user/tasks";
    }
}
