package com.example.controller;

import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final TaskService taskService;
    private final UserService userService;

    public UserController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    // ---------------- DASHBOARD ----------------
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User currentUser = userService.findByUsername(auth.getName()).orElseThrow();

        List<TaskAssignment> assignments = taskService.getAssignmentsForUser(currentUser);

        // calculate stats
        long totalTasks = assignments.size();
        long completedTasks = assignments.stream()
                .filter(a -> a.getTask().getStatus().name().equals("COMPLETED"))
                .count();
        long inProgressTasks = assignments.stream()
                .filter(a -> a.getTask().getStatus().name().equals("IN_PROGRESS"))
                .count();

        model.addAttribute("user", currentUser);
        model.addAttribute("assignments", assignments);
        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);

        return "user/dashboard"; // ✅ Thymeleaf template
    }

    // ---------------- VIEW TASKS ----------------
    @GetMapping("/tasks")
    public String viewTasks(Authentication auth, Model model) {
        User currentUser = userService.findByUsername(auth.getName()).orElseThrow();
        List<TaskAssignment> assignments = taskService.getAssignmentsForUser(currentUser);
        model.addAttribute("assignments", assignments);
        return "user/tasks"; // ✅ Thymeleaf template
    }
}
