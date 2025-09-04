package com.example.controller;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.service.CommentService;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;
    private final UserService userService;

    public TaskController(TaskService taskService, CommentService commentService, UserService userService) {
        this.taskService = taskService;
        this.commentService = commentService;
        this.userService = userService;
    }

    // ---------------- UPDATE TASK STATUS ----------------
    @PostMapping("/task/{id}/status")
    public String updateTaskStatus(@PathVariable Long id, @RequestParam String status) {
        Task task = taskService.findById(id).orElseThrow();
        switch (status) {
            case "COMPLETED" -> task.setStatus(Task.Status.COMPLETED);
            case "IN_PROGRESS" -> task.setStatus(Task.Status.IN_PROGRESS);
            default -> task.setStatus(Task.Status.PENDING);
        }
        taskService.create(task);
        return "redirect:/user/tasks";
    }

    // ---------------- ADD COMMENT ----------------
    @PostMapping("/task/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String text, Authentication auth) {
        Task task = taskService.findById(id).orElseThrow();
        User user = userService.findByUsername(auth.getName()).orElseThrow();

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUser(user);
        comment.setText(text);

        commentService.save(comment);
        return "redirect:/user/tasks";
    }
}
